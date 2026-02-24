import { useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { Search as SearchIcon } from "lucide-react";
import { motion } from "framer-motion";
import { cn } from "@/lib/utils";
import { useDebounce } from "@/hooks/useDebounce";
import { useSearch } from "@/hooks/useSearch";
import { PostCard } from "@/components/posts/PostCard";
import { PostSkeleton } from "@/components/posts/PostSkeleton";
import { UserAvatar } from "@/components/UserAvatar";
import { Input } from "@/components/ui/input";
type Tab = "all" | "posts" | "users";
export default function SearchPage() {
  const [searchParams] = useSearchParams();
  const [query, setQuery] = useState(searchParams.get("q") || "");
  const [tab, setTab] = useState<Tab>("all");
  const debounced = useDebounce(query, 300);
  const { data, isLoading } = useSearch(debounced);
  const tabLabels: Record<Tab, string> = {
    all: "Все",
    posts: "Посты",
    users: "Пользователи",
  };
  return (
    <div>
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border p-4 space-y-3">
        <div className="relative">
          <SearchIcon className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
          <Input
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            placeholder="Поиск постов и пользователей..."
            className="pl-9 bg-secondary border-border"
          />
        </div>
        <div className="flex gap-1">
          {(["all", "posts", "users"] as const).map((t) => (
            <motion.button
              key={t}
              whileTap={{ scale: 0.95 }}
              onClick={() => setTab(t)}
              className={cn(
                "px-3 py-1.5 rounded-full text-xs font-medium transition-colors",
                tab === t
                  ? "bg-primary text-primary-foreground"
                  : "text-muted-foreground hover:bg-secondary"
              )}
            >
              {tabLabels[t]}
            </motion.button>
          ))}
        </div>
      </div>
      {!debounced ? (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="py-20 text-center text-muted-foreground text-sm"
        >
          Введите запрос для поиска
        </motion.div>
      ) : isLoading ? (
        Array.from({ length: 3 }).map((_, i) => <PostSkeleton key={i} />)
      ) : (
        <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ duration: 0.2 }}>
          {(tab === "all" || tab === "users") &&
            data?.users &&
            data.users.length > 0 && (
              <div className="p-4 space-y-2">
                <h3 className="text-sm font-semibold text-muted-foreground">Пользователи</h3>
                {data.users.map((u) => (
                  <Link
                    key={u.id}
                    to={`/profile/${u.id}`}
                    className="flex items-center gap-3 p-2 rounded-lg hover:bg-secondary/50 transition-colors"
                  >
                    <UserAvatar user={u} size="sm" />
                    <div>
                      <p className="text-sm font-medium text-foreground">{u.username}</p>
                      {u.bio && (
                        <p className="text-xs text-muted-foreground truncate max-w-[200px]">{u.bio}</p>
                      )}
                    </div>
                  </Link>
                ))}
              </div>
            )}
          {(tab === "all" || tab === "posts") &&
            data?.posts &&
            data.posts.map((p, i) => <PostCard key={p.id} post={p} index={i} />)}
          {data && !data.posts?.length && !data.users?.length && (
            <div className="py-20 text-center text-muted-foreground text-sm">
              Ничего не найдено по запросу «{debounced}»
            </div>
          )}
        </motion.div>
      )}
    </div>
  );
}
