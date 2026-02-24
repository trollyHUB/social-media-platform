import { useState, useRef, useEffect } from "react";
import { Loader2 } from "lucide-react";
import { motion } from "framer-motion";
import { toast } from "sonner";
import { useAuthStore } from "@/store/authStore";
import { useCreatePost } from "@/hooks/usePosts";
import { UserAvatar } from "@/components/UserAvatar";
import { Button } from "@/components/ui/button";
const MAX_CHARS = 500;
export function CreatePostForm() {
  const user = useAuthStore((s) => s.user);
  const createPost = useCreatePost();
  const [content, setContent] = useState("");
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  useEffect(() => {
    const el = textareaRef.current;
    if (!el) return;
    el.style.height = "auto";
    el.style.height = `${el.scrollHeight}px`;
  }, [content]);
  const handleSubmit = () => {
    if (!content.trim() || content.length > MAX_CHARS || createPost.isPending) return;
    createPost.mutate(
      { content },
      {
        onSuccess: () => {
          setContent("");
          toast.success("Пост опубликован!");
        },
        onError: () => toast.error("Ошибка публикации"),
      }
    );
  };
  if (!user) return null;
  return (
    <div className="p-4 border-b border-border">
      <div className="flex gap-3">
        <UserAvatar user={user} size="md" />
        <div className="flex-1">
          <textarea
            ref={textareaRef}
            value={content}
            onChange={(e) => setContent(e.target.value)}
            placeholder="Что нового?"
            className="w-full bg-transparent text-foreground text-sm resize-none outline-none placeholder:text-muted-foreground min-h-[60px] max-h-[200px]"
            rows={2}
            onKeyDown={(e) => {
              if (e.key === "Enter" && (e.ctrlKey || e.metaKey)) handleSubmit();
            }}
          />
          <div className="flex items-center justify-between mt-2">
            <span
              className={`text-xs transition-colors ${
                content.length > MAX_CHARS ? "text-destructive" : "text-muted-foreground"
              }`}
            >
              {content.length}/{MAX_CHARS}
            </span>
            <Button
              size="sm"
              onClick={handleSubmit}
              disabled={!content.trim() || content.length > MAX_CHARS || createPost.isPending}
            >
              {createPost.isPending ? (
                <Loader2 className="h-4 w-4 animate-spin" />
              ) : (
                "Опубликовать"
              )}
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}
