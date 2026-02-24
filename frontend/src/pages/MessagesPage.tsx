import { useNavigate } from "react-router-dom";
import { MessageCircle } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { ru } from "date-fns/locale";
import { motion } from "framer-motion";
import { useChats } from "@/hooks/useMessages";
import { UserAvatar } from "@/components/UserAvatar";
import { PostSkeleton } from "@/components/posts/PostSkeleton";
export default function MessagesPage() {
  const { data: chats, isLoading } = useChats();
  const navigate = useNavigate();
  return (
    <div>
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3">
        <h1 className="text-lg font-bold text-foreground">Сообщения</h1>
      </div>
      {isLoading ? (
        Array.from({ length: 3 }).map((_, i) => <PostSkeleton key={i} />)
      ) : chats && chats.length > 0 ? (
        chats.map((chat, i) => (
          <motion.div
            key={chat.userId}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: i * 0.05, duration: 0.25 }}
            onClick={() => navigate(`/messages/${chat.userId}`)}
            className="flex items-center gap-3 p-4 border-b border-border hover:bg-secondary/30 transition-colors cursor-pointer"
          >
            <UserAvatar user={chat.user} size="md" />
            <div className="flex-1 min-w-0">
              <div className="flex items-center justify-between">
                <p className="text-sm font-semibold text-foreground">{chat.user.username}</p>
                <span className="text-xs text-muted-foreground">
                  {formatDistanceToNow(new Date(chat.lastMessage.createdAt), { addSuffix: true, locale: ru })}
                </span>
              </div>
              <p className="text-xs text-muted-foreground truncate mt-0.5">
                {chat.lastMessage.content}
              </p>
            </div>
            {chat.unreadCount > 0 && (
              <motion.span
                initial={{ scale: 0 }}
                animate={{ scale: 1 }}
                className="bg-primary text-primary-foreground text-xs rounded-full h-5 min-w-5 flex items-center justify-center px-1 shrink-0"
              >
                {chat.unreadCount}
              </motion.span>
            )}
          </motion.div>
        ))
      ) : (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="py-20 text-center text-muted-foreground text-sm"
        >
          <MessageCircle className="h-12 w-12 mx-auto mb-3 opacity-50" />
          Нет сообщений
        </motion.div>
      )}
    </div>
  );
}
