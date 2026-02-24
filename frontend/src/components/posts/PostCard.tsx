import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Heart, MessageCircle, Share2, Trash2 } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { ru } from "date-fns/locale";
import { motion } from "framer-motion";
import { UserAvatar } from "@/components/UserAvatar";
import { useAuthStore } from "@/store/authStore";
import { useLikePost, useDeletePost } from "@/hooks/usePosts";
import { toast } from "sonner";
import type { Post } from "@/types";
import { cn } from "@/lib/utils";
interface PostCardProps {
  post: Post;
  index?: number;
}
export const postVariants = {
  hidden: { opacity: 0, y: 20 },
  show: (i: number) => ({
    opacity: 1,
    y: 0,
    transition: {
      delay: i * 0.05,
      duration: 0.3,
      ease: "easeOut" as const,
    },
  }),
};
export function PostCard({ post, index = 0 }: PostCardProps) {
  const navigate = useNavigate();
  const user = useAuthStore((s) => s.user);
  const likeMutation = useLikePost();
  const deleteMutation = useDeletePost();
  const [animateLike, setAnimateLike] = useState(false);
  const isOwner = user?.id === post.author.id;
  const timeAgo = formatDistanceToNow(new Date(post.createdAt), {
    addSuffix: true,
    locale: ru,
  });
  const handleLike = (e: React.MouseEvent) => {
    e.stopPropagation();
    setAnimateLike(true);
    setTimeout(() => setAnimateLike(false), 400);
    likeMutation.mutate(post.id);
  };
  const handleDelete = (e: React.MouseEvent) => {
    e.stopPropagation();
    if (confirm("Удалить пост?")) {
      deleteMutation.mutate(post.id, {
        onSuccess: () => toast.success("Пост удалён"),
      });
    }
  };
  return (
    <motion.div
      custom={index}
      variants={postVariants}
      initial="hidden"
      animate="show"
      className="p-4 border-b border-border hover:bg-secondary/30 transition-colors cursor-pointer"
      onClick={() => navigate(`/post/${post.id}`)}
    >
      <div className="flex gap-3">
        <Link to={`/profile/${post.author.id}`} onClick={(e) => e.stopPropagation()}>
          <UserAvatar user={post.author} size="md" />
        </Link>
        <div className="flex-1 min-w-0">
          <div className="flex items-center gap-2">
            <Link
              to={`/profile/${post.author.id}`}
              onClick={(e) => e.stopPropagation()}
              className="text-sm font-semibold text-foreground hover:underline"
            >
              {post.author.username}
            </Link>
            <span className="text-xs text-muted-foreground">{timeAgo}</span>
          </div>
          <p className="text-sm text-foreground mt-1 whitespace-pre-wrap break-words">
            {post.content}
          </p>
          {post.imageUrl && (
            <img
              src={post.imageUrl}
              alt=""
              className="mt-2 rounded-lg max-h-80 object-cover w-full"
            />
          )}
          <div className="flex items-center gap-6 mt-3">
            <button
              onClick={handleLike}
              className={cn(
                "flex items-center gap-1.5 text-sm transition-colors",
                post.liked
                  ? "text-destructive"
                  : "text-muted-foreground hover:text-destructive"
              )}
            >
              <motion.div
                animate={animateLike ? { scale: [1, 1.5, 0.9, 1] } : {}}
                transition={{ duration: 0.35, ease: "easeOut" }}
              >
                <Heart
                  className={cn(
                    "h-4 w-4 transition-all",
                    post.liked && "fill-current"
                  )}
                />
              </motion.div>
              <span>{post.likes}</span>
            </button>
            <button className="flex items-center gap-1.5 text-sm text-muted-foreground hover:text-primary transition-colors">
              <MessageCircle className="h-4 w-4" />
              <span>{post.commentsCount}</span>
            </button>
            <button
              onClick={(e) => {
                e.stopPropagation();
                navigator.clipboard.writeText(
                  `${window.location.origin}/post/${post.id}`
                );
                toast.success("Ссылка скопирована");
              }}
              className="flex items-center gap-1.5 text-sm text-muted-foreground hover:text-primary transition-colors"
            >
              <Share2 className="h-4 w-4" />
            </button>
            {isOwner && (
              <button
                onClick={handleDelete}
                className="ml-auto text-muted-foreground hover:text-destructive transition-colors"
              >
                <Trash2 className="h-4 w-4" />
              </button>
            )}
          </div>
        </div>
      </div>
    </motion.div>
  );
}
