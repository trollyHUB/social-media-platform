import { motion } from "framer-motion";
import { usePosts } from "@/hooks/usePosts";
import { useAuthStore } from "@/store/authStore";
import { PostCard } from "@/components/posts/PostCard";
import { PostSkeleton } from "@/components/posts/PostSkeleton";
import { CreatePostForm } from "@/components/posts/CreatePostForm";
import { FileText } from "lucide-react";
const listVariants = {
  hidden: {},
  show: {
    transition: {
      staggerChildren: 0.05,
    },
  },
};
export default function HomePage() {
  const { data: posts, isLoading } = usePosts();
  const isAuth = useAuthStore((s) => s.isAuthenticated);
  return (
    <div>
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3">
        <h1 className="text-lg font-bold text-foreground">Главная</h1>
      </div>
      {isAuth && <CreatePostForm />}
      {isLoading ? (
        Array.from({ length: 5 }).map((_, i) => <PostSkeleton key={i} />)
      ) : posts && posts.length > 0 ? (
        <motion.div variants={listVariants} initial="hidden" animate="show">
          {posts.map((post, index) => (
            <PostCard key={post.id} post={post} index={index} />
          ))}
        </motion.div>
      ) : (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.4 }}
          className="flex flex-col items-center justify-center py-20 text-muted-foreground"
        >
          <FileText className="h-12 w-12 mb-3" />
          <p className="text-sm">Пока нет постов. Будьте первым!</p>
        </motion.div>
      )}
    </div>
  );
}
