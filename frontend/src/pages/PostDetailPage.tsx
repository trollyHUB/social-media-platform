import { useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ArrowLeft, Loader2, Trash2 } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { ru } from "date-fns/locale";
import { motion, AnimatePresence } from "framer-motion";
import { useAuthStore } from "@/store/authStore";
import { usePost } from "@/hooks/usePosts";
import { useComments, useAddComment, useDeleteComment } from "@/hooks/useComments";
import { PostCard } from "@/components/posts/PostCard";
import { PostSkeleton } from "@/components/posts/PostSkeleton";
import { UserAvatar } from "@/components/UserAvatar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
export default function PostDetailPage() {
  const { id } = useParams<{ id: string }>();
  const postId = Number(id);
  const { data: post, isLoading } = usePost(postId);
  const { data: comments, isLoading: commentsLoading } = useComments(postId);
  const addComment = useAddComment(postId);
  const deleteComment = useDeleteComment();
  const user = useAuthStore((s) => s.user);
  const [commentText, setCommentText] = useState("");
  const handleAddComment = () => {
    if (!commentText.trim() || addComment.isPending) return;
    addComment.mutate(
      { content: commentText },
      { onSuccess: () => setCommentText("") }
    );
  };
  if (isLoading) return <PostSkeleton />;
  if (!post) return <div className="p-8 text-center text-muted-foreground">Пост не найден</div>;
  return (
    <div className="flex flex-col h-full">
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3 flex items-center gap-3">
        <Link to="/" className="text-foreground hover:text-primary transition-colors">
          <ArrowLeft className="h-5 w-5" />
        </Link>
        <h1 className="text-lg font-bold text-foreground">Пост</h1>
      </div>
      <PostCard post={post} />
      {/* Comment form */}
      {user && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="p-4 border-b border-border flex gap-3"
        >
          <UserAvatar user={user} size="sm" />
          <div className="flex-1 flex gap-2">
            <Input
              value={commentText}
              onChange={(e) => setCommentText(e.target.value)}
              placeholder="Написать комментарий..."
              className="bg-secondary border-border flex-1"
              maxLength={300}
              onKeyDown={(e) => e.key === "Enter" && handleAddComment()}
            />
            <Button
              size="sm"
              onClick={handleAddComment}
              disabled={!commentText.trim() || addComment.isPending}
            >
              {addComment.isPending ? (
                <Loader2 className="h-4 w-4 animate-spin" />
              ) : (
                "Отправить"
              )}
            </Button>
          </div>
        </motion.div>
      )}
      {/* Comments list */}
      <div>
        {commentsLoading ? (
          <div className="p-4 text-center text-muted-foreground text-sm">Загрузка...</div>
        ) : comments && comments.length > 0 ? (
          <AnimatePresence>
            {comments.map((c, i) => (
              <motion.div
                key={c.id}
                initial={{ opacity: 0, y: 8 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: i * 0.04, duration: 0.2 }}
                className="p-4 border-b border-border flex gap-3"
              >
                <Link to={`/profile/${c.author.id}`}>
                  <UserAvatar user={c.author} size="sm" />
                </Link>
                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <Link
                      to={`/profile/${c.author.id}`}
                      className="text-sm font-semibold text-foreground hover:underline"
                    >
                      {c.author.username}
                    </Link>
                    <span className="text-xs text-muted-foreground">
                      {formatDistanceToNow(new Date(c.createdAt), { addSuffix: true, locale: ru })}
                    </span>
                    {user?.id === c.author.id && (
                      <button
                        onClick={() => deleteComment.mutate(c.id)}
                        className="ml-auto text-muted-foreground hover:text-destructive transition-colors"
                      >
                        <Trash2 className="h-3 w-3" />
                      </button>
                    )}
                  </div>
                  <p className="text-sm text-foreground mt-0.5">{c.content}</p>
                </div>
              </motion.div>
            ))}
          </AnimatePresence>
        ) : (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="py-8 text-center text-muted-foreground text-sm"
          >
            Нет комментариев. Будьте первым!
          </motion.div>
        )}
      </div>
    </div>
  );
}
