import { Link, useParams } from "react-router-dom";
import { ArrowLeft, Calendar } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { ru } from "date-fns/locale";
import { motion } from "framer-motion";
import { useAuthStore } from "@/store/authStore";
import { useUser, useFollowUser, useUnfollowUser } from "@/hooks/useUsers";
import { useUserPosts } from "@/hooks/usePosts";
import { PostCard } from "@/components/posts/PostCard";
import { PostSkeleton } from "@/components/posts/PostSkeleton";
import { UserAvatar } from "@/components/UserAvatar";
import { Button } from "@/components/ui/button";
export default function ProfilePage() {
  const { id } = useParams<{ id: string }>();
  const userId = Number(id);
  const { data: profileUser, isLoading } = useUser(userId);
  const { data: posts, isLoading: postsLoading } = useUserPosts(userId);
  const currentUser = useAuthStore((s) => s.user);
  const followMutation = useFollowUser();
  const unfollowMutation = useUnfollowUser();
  const isOwn = currentUser?.id === userId;
  if (isLoading) {
    return <div className="p-8"><PostSkeleton /><PostSkeleton /></div>;
  }
  if (!profileUser) {
    return <div className="p-8 text-center text-muted-foreground">Пользователь не найден</div>;
  }
  return (
    <div>
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3 flex items-center gap-3">
        <Link to="/" className="text-foreground hover:text-primary">
          <ArrowLeft className="h-5 w-5" />
        </Link>
        <h1 className="text-lg font-bold text-foreground">{profileUser.username}</h1>
      </div>
      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.3 }}
        className="p-6 border-b border-border"
      >
        <div className="flex items-start gap-4">
          <UserAvatar user={profileUser} size="xl" />
          <div className="flex-1 min-w-0">
            <div className="flex items-center justify-between gap-2">
              <div>
                <h2 className="text-xl font-bold text-foreground">{profileUser.username}</h2>
                <p className="text-sm text-muted-foreground">{profileUser.email}</p>
              </div>
              {!isOwn && currentUser && (
                <Button
                  size="sm"
                  variant="outline"
                  onClick={() => followMutation.mutate(userId)}
                  disabled={followMutation.isPending}
                >
                  Подписаться
                </Button>
              )}
            </div>
            {profileUser.bio && (
              <p className="text-sm text-foreground mt-2">{profileUser.bio}</p>
            )}
            <div className="flex items-center gap-4 mt-3 text-sm text-muted-foreground">
              <span>
                <strong className="text-foreground">{profileUser.postsCount ?? 0}</strong> постов
              </span>
              <span>
                <strong className="text-foreground">{profileUser.followersCount ?? 0}</strong> подписчиков
              </span>
              <span>
                <strong className="text-foreground">{profileUser.followingCount ?? 0}</strong> подписок
              </span>
            </div>
            <div className="flex items-center gap-1 mt-2 text-xs text-muted-foreground">
              <Calendar className="h-3 w-3" />
              <span>
                Присоединился{" "}
                {formatDistanceToNow(new Date(profileUser.createdAt), { addSuffix: true, locale: ru })}
              </span>
            </div>
          </div>
        </div>
      </motion.div>
      {postsLoading ? (
        Array.from({ length: 3 }).map((_, i) => <PostSkeleton key={i} />)
      ) : posts && posts.length > 0 ? (
        posts.map((post, i) => <PostCard key={post.id} post={post} index={i} />)
      ) : (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="py-12 text-center text-muted-foreground text-sm"
        >
          Нет постов
        </motion.div>
      )}
    </div>
  );
}
