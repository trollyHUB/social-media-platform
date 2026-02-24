import { useState } from "react";
import { Search, Users, FileText, Heart, MessageSquare } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import { useStats } from "@/hooks/useStats";
import { useSuggestedUsers } from "@/hooks/useUsers";
import { UserAvatar } from "@/components/UserAvatar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
export function RightSidebar() {
  const [searchQuery, setSearchQuery] = useState("");
  const navigate = useNavigate();
  const { data: stats } = useStats();
  const { data: suggested } = useSuggestedUsers();
  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery.trim())}`);
      setSearchQuery("");
    }
  };
  return (
    <aside className="hidden lg:flex flex-col w-[300px] h-screen sticky top-0 border-l border-border bg-background p-4 gap-4 shrink-0 overflow-y-auto">
      {/* Search */}
      <form onSubmit={handleSearch}>
        <div className="relative">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
          <Input
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Поиск..."
            className="pl-9 bg-secondary border-border"
          />
        </div>
      </form>
      {/* Stats */}
      {stats && (
        <motion.div
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3 }}
          className="bg-card rounded-lg p-4 space-y-3"
        >
          <h3 className="font-semibold text-foreground text-sm">Статистика платформы</h3>
          <div className="grid grid-cols-2 gap-3">
            <StatItem icon={Users} label="Пользователи" value={stats.totalUsers} />
            <StatItem icon={FileText} label="Посты" value={stats.totalPosts} />
            <StatItem icon={Heart} label="Лайки" value={stats.totalLikes} />
            <StatItem icon={MessageSquare} label="Комменты" value={stats.totalComments} />
          </div>
        </motion.div>
      )}
      {/* Suggested */}
      {suggested && suggested.length > 0 && (
        <motion.div
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3, delay: 0.1 }}
          className="bg-card rounded-lg p-4 space-y-3"
        >
          <h3 className="font-semibold text-foreground text-sm">Рекомендуемые</h3>
          <div className="space-y-3">
            {suggested.slice(0, 5).map((user) => (
              <motion.div
                key={user.id}
                whileHover={{ x: 4 }}
                transition={{ duration: 0.15 }}
                className="flex items-center gap-3 cursor-pointer hover:bg-secondary/50 -mx-2 px-2 py-1.5 rounded-md transition-colors"
                onClick={() => navigate(`/profile/${user.id}`)}
              >
                <UserAvatar user={user} size="sm" />
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-foreground truncate">{user.username}</p>
                  {user.bio && <p className="text-xs text-muted-foreground truncate">{user.bio}</p>}
                </div>
              </motion.div>
            ))}
          </div>
        </motion.div>
      )}
    </aside>
  );
}
function StatItem({ icon: Icon, label, value }: { icon: any; label: string; value: number }) {
  return (
    <div className="flex items-center gap-2">
      <Icon className="h-4 w-4 text-primary shrink-0" />
      <div className="min-w-0">
        <p className="text-sm font-semibold text-foreground">{value.toLocaleString()}</p>
        <p className="text-xs text-muted-foreground truncate">{label}</p>
      </div>
    </div>
  );
}
