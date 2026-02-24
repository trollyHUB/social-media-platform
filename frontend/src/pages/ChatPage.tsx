import { useState, useRef, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { ArrowLeft, Send, Loader2 } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { ru } from "date-fns/locale";
import { motion, AnimatePresence } from "framer-motion";
import { cn } from "@/lib/utils";
import { useAuthStore } from "@/store/authStore";
import { useUser } from "@/hooks/useUsers";
import { useMessages, useSendMessage } from "@/hooks/useMessages";
import { UserAvatar } from "@/components/UserAvatar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
export default function ChatPage() {
  const { userId } = useParams<{ userId: string }>();
  const recipientId = Number(userId);
  const currentUser = useAuthStore((s) => s.user);
  const { data: recipient } = useUser(recipientId);
  const { data: messages, isLoading } = useMessages(recipientId);
  const sendMessage = useSendMessage();
  const [text, setText] = useState("");
  const bottomRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);
  const handleSend = () => {
    if (!text.trim() || sendMessage.isPending) return;
    sendMessage.mutate(
      { recipientId, content: text },
      { onSuccess: () => setText("") }
    );
  };
  return (
    <div className="flex flex-col h-screen">
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3 flex items-center gap-3 shrink-0">
        <Link to="/messages" className="text-foreground hover:text-primary transition-colors">
          <ArrowLeft className="h-5 w-5" />
        </Link>
        {recipient && (
          <div className="flex items-center gap-2">
            <UserAvatar user={recipient} size="sm" />
            <span className="font-semibold text-foreground text-sm">{recipient.username}</span>
          </div>
        )}
      </div>
      <div className="flex-1 overflow-y-auto p-4 space-y-3">
        {isLoading ? (
          <div className="text-center text-muted-foreground text-sm py-8">Загрузка...</div>
        ) : messages && messages.length > 0 ? (
          <AnimatePresence initial={false}>
            {messages.map((m) => {
              const isMine = m.senderId === currentUser?.id;
              return (
                <motion.div
                  key={m.id}
                  initial={{ opacity: 0, y: 8, scale: 0.95 }}
                  animate={{ opacity: 1, y: 0, scale: 1 }}
                  transition={{ duration: 0.2 }}
                  className={cn("flex", isMine ? "justify-end" : "justify-start")}
                >
                  <div
                    className={cn(
                      "max-w-[70%] rounded-2xl px-4 py-2",
                      isMine
                        ? "bg-primary text-primary-foreground rounded-br-md"
                        : "bg-card text-card-foreground rounded-bl-md"
                    )}
                  >
                    <p className="text-sm">{m.content}</p>
                    <p
                      className={cn(
                        "text-[10px] mt-1",
                        isMine ? "text-primary-foreground/60" : "text-muted-foreground"
                      )}
                    >
                      {formatDistanceToNow(new Date(m.createdAt), { addSuffix: true, locale: ru })}
                    </p>
                  </div>
                </motion.div>
              );
            })}
          </AnimatePresence>
        ) : (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-center text-muted-foreground text-sm py-8"
          >
            Начните переписку
          </motion.div>
        )}
        <div ref={bottomRef} />
      </div>
      <div className="border-t border-border p-3 flex gap-2 shrink-0 bg-background">
        <Input
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="Сообщение..."
          className="bg-secondary border-border flex-1"
          onKeyDown={(e) => e.key === "Enter" && handleSend()}
        />
        <Button
          size="icon"
          onClick={handleSend}
          disabled={!text.trim() || sendMessage.isPending}
        >
          {sendMessage.isPending ? (
            <Loader2 className="h-4 w-4 animate-spin" />
          ) : (
            <Send className="h-4 w-4" />
          )}
        </Button>
      </div>
    </div>
  );
}
