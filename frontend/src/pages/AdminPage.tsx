import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import {
  Shield, Users, FileText, TrendingUp, Ban, CheckCircle,
  Trash2, Crown, AlertTriangle, RefreshCw, Search
} from "lucide-react";
import { Input } from "@/components/ui/input";
import { useAuthStore } from "@/store/authStore";
import { toast } from "sonner";
import apiClient from "@/api/client";
import { Navigate } from "react-router-dom";

// ---- API ----
const adminApi = {
  getStats: () => apiClient.get("/admin/stats").then(r => r.data),
  getUsers: () => apiClient.get("/admin/users").then(r => r.data),
  changeRole: (id: number, role: string) =>
    apiClient.put(`/admin/users/${id}/role`, { role }).then(r => r.data),
  toggleBan: (id: number) =>
    apiClient.put(`/admin/users/${id}/toggle-ban`).then(r => r.data),
  deleteUser: (id: number) =>
    apiClient.delete(`/admin/users/${id}`).then(r => r.data),
  deletePost: (id: number) =>
    apiClient.delete(`/admin/posts/${id}`).then(r => r.data),
};

const ROLE_LABELS: Record<string, { label: string; color: string }> = {
  ADMIN:     { label: "Админ",      color: "text-red-500 bg-red-500/10" },
  MODERATOR: { label: "Модератор",  color: "text-orange-500 bg-orange-500/10" },
  USER:      { label: "Пользователь", color: "text-blue-500 bg-blue-500/10" },
};

export default function AdminPage() {
  const { user } = useAuthStore();
  const [search, setSearch] = useState("");
  const [activeTab, setActiveTab] = useState<"overview" | "users">("overview");
  const qc = useQueryClient();

  // Guard — только ADMIN
  if (!user || user.role !== "ADMIN") {
    return <Navigate to="/feed" replace />;
  }

  const { data: stats, isLoading: statsLoading } = useQuery({
    queryKey: ["admin", "stats"],
    queryFn: adminApi.getStats,
  });

  const { data: users = [], isLoading: usersLoading } = useQuery({
    queryKey: ["admin", "users"],
    queryFn: adminApi.getUsers,
  });

  const changeRoleMutation = useMutation({
    mutationFn: ({ id, role }: { id: number; role: string }) =>
      adminApi.changeRole(id, role),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["admin"] });
      toast.success("Роль изменена");
    },
    onError: () => toast.error("Ошибка изменения роли"),
  });

  const toggleBanMutation = useMutation({
    mutationFn: (id: number) => adminApi.toggleBan(id),
    onSuccess: (data) => {
      qc.invalidateQueries({ queryKey: ["admin"] });
      toast.success(data.message);
    },
  });

  const deleteUserMutation = useMutation({
    mutationFn: (id: number) => adminApi.deleteUser(id),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["admin"] });
      toast.success("Пользователь удалён");
    },
  });

  const filteredUsers = users.filter((u: any) =>
    u.username.toLowerCase().includes(search.toLowerCase()) ||
    u.email.toLowerCase().includes(search.toLowerCase())
  );

  const statCards = [
    { label: "Всего пользователей", value: stats?.totalUsers ?? "—", icon: Users, color: "text-blue-500" },
    { label: "Постов",              value: stats?.totalPosts ?? "—",  icon: FileText, color: "text-green-500" },
    { label: "Администраторов",     value: stats?.admins ?? "—",      icon: Crown, color: "text-red-500" },
    { label: "Заблокировано",       value: stats?.disabledUsers ?? "—", icon: Ban, color: "text-orange-500" },
  ];

  return (
    <div className="max-w-5xl mx-auto p-4 space-y-6">
      {/* Header */}
      <motion.div
        initial={{ opacity: 0, y: -10 }}
        animate={{ opacity: 1, y: 0 }}
        className="flex items-center gap-3 py-4 border-b border-border"
      >
        <div className="h-10 w-10 rounded-xl bg-red-500/10 flex items-center justify-center">
          <Shield className="h-5 w-5 text-red-500" />
        </div>
        <div>
          <h1 className="text-xl font-bold text-foreground">Панель администратора</h1>
          <p className="text-xs text-muted-foreground">Управление платформой NEXIS Connect</p>
        </div>
        <button
          onClick={() => qc.invalidateQueries({ queryKey: ["admin"] })}
          className="ml-auto p-2 rounded-lg hover:bg-secondary transition-colors"
          title="Обновить"
        >
          <RefreshCw className="h-4 w-4 text-muted-foreground" />
        </button>
      </motion.div>

      {/* Tabs */}
      <div className="flex gap-2">
        {[
          { key: "overview", label: "Обзор", icon: TrendingUp },
          { key: "users",    label: "Пользователи", icon: Users },
        ].map(tab => (
          <button
            key={tab.key}
            onClick={() => setActiveTab(tab.key as any)}
            className={`flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
              activeTab === tab.key
                ? "bg-primary text-primary-foreground"
                : "bg-secondary text-muted-foreground hover:text-foreground"
            }`}
          >
            <tab.icon className="h-4 w-4" />
            {tab.label}
          </button>
        ))}
      </div>

      <AnimatePresence mode="wait">
        {/* OVERVIEW TAB */}
        {activeTab === "overview" && (
          <motion.div
            key="overview"
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0 }}
            className="space-y-4"
          >
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
              {statCards.map((s, i) => (
                <motion.div
                  key={s.label}
                  initial={{ opacity: 0, scale: 0.95 }}
                  animate={{ opacity: 1, scale: 1 }}
                  transition={{ delay: i * 0.07 }}
                  className="p-4 rounded-2xl border border-border bg-card"
                >
                  <s.icon className={`h-5 w-5 mb-2 ${s.color}`} />
                  <div className="text-2xl font-black text-foreground">
                    {statsLoading ? "..." : s.value}
                  </div>
                  <div className="text-xs text-muted-foreground mt-0.5">{s.label}</div>
                </motion.div>
              ))}
            </div>

            {/* Warning box */}
            <div className="p-4 rounded-2xl border border-orange-500/20 bg-orange-500/5 flex items-start gap-3">
              <AlertTriangle className="h-5 w-5 text-orange-500 shrink-0 mt-0.5" />
              <div>
                <p className="text-sm font-semibold text-foreground">Зона повышенной ответственности</p>
                <p className="text-xs text-muted-foreground mt-1">
                  Действия в этой панели необратимы. Удаление пользователей и постов происходит без возможности восстановления.
                  Изменение ролей вступает в силу немедленно.
                </p>
              </div>
            </div>
          </motion.div>
        )}

        {/* USERS TAB */}
        {activeTab === "users" && (
          <motion.div
            key="users"
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0 }}
            className="space-y-4"
          >
            {/* Search */}
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
              <Input
                placeholder="Поиск по username или email..."
                value={search}
                onChange={e => setSearch(e.target.value)}
                className="pl-9"
              />
            </div>

            {/* Users list */}
            <div className="space-y-2">
              {usersLoading ? (
                Array.from({ length: 5 }).map((_, i) => (
                  <div key={i} className="h-16 rounded-xl bg-secondary animate-pulse" />
                ))
              ) : filteredUsers.map((u: any, i: number) => (
                <motion.div
                  key={u.id}
                  initial={{ opacity: 0, x: -10 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: i * 0.03 }}
                  className={`flex items-center gap-3 p-3 rounded-xl border transition-colors ${
                    !u.enabled
                      ? "border-red-500/20 bg-red-500/5"
                      : "border-border bg-card hover:bg-secondary/50"
                  }`}
                >
                  {/* Avatar */}
                  <div
                    className="h-9 w-9 rounded-full flex items-center justify-center text-white text-sm font-bold shrink-0"
                    style={{ backgroundColor: u.avatarColor || "#6c63ff" }}
                  >
                    {u.username[0].toUpperCase()}
                  </div>

                  {/* Info */}
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center gap-2">
                      <span className="text-sm font-semibold text-foreground">{u.username}</span>
                      <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${ROLE_LABELS[u.role]?.color}`}>
                        {ROLE_LABELS[u.role]?.label}
                      </span>
                      {!u.enabled && (
                        <span className="text-xs px-2 py-0.5 rounded-full bg-red-500/10 text-red-500 font-medium">
                          Заблокирован
                        </span>
                      )}
                    </div>
                    <p className="text-xs text-muted-foreground truncate">{u.email}</p>
                  </div>

                  {/* Actions — не показываем для самого себя */}
                  {u.username !== user.username && (
                    <div className="flex items-center gap-1 shrink-0">
                      {/* Change role */}
                      <select
                        value={u.role}
                        onChange={e => changeRoleMutation.mutate({ id: u.id, role: e.target.value })}
                        className="text-xs bg-secondary border border-border rounded-lg px-2 py-1 text-foreground cursor-pointer"
                      >
                        <option value="USER">USER</option>
                        <option value="MODERATOR">MODERATOR</option>
                        <option value="ADMIN">ADMIN</option>
                      </select>

                      {/* Ban/Unban */}
                      <button
                        onClick={() => toggleBanMutation.mutate(u.id)}
                        className={`p-1.5 rounded-lg transition-colors ${
                          u.enabled
                            ? "hover:bg-orange-500/10 text-muted-foreground hover:text-orange-500"
                            : "bg-green-500/10 text-green-500 hover:bg-green-500/20"
                        }`}
                        title={u.enabled ? "Заблокировать" : "Разблокировать"}
                      >
                        {u.enabled ? <Ban className="h-4 w-4" /> : <CheckCircle className="h-4 w-4" />}
                      </button>

                      {/* Delete */}
                      <button
                        onClick={() => {
                          if (confirm(`Удалить пользователя ${u.username}?`)) {
                            deleteUserMutation.mutate(u.id);
                          }
                        }}
                        className="p-1.5 rounded-lg hover:bg-red-500/10 text-muted-foreground hover:text-red-500 transition-colors"
                        title="Удалить"
                      >
                        <Trash2 className="h-4 w-4" />
                      </button>
                    </div>
                  )}
                </motion.div>
              ))}
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
