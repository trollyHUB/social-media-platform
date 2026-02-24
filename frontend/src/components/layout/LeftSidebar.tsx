import { Link, useLocation } from "react-router-dom";
import { Home, Search, Bell, MessageCircle, Settings, User, LogOut, Shield } from "lucide-react";
import { motion } from "framer-motion";
import { cn } from "@/lib/utils";
import { useAuthStore } from "@/store/authStore";
import { useUnreadCount } from "@/hooks/useNotifications";
import { UserAvatar } from "@/components/UserAvatar";
const navItems = [
  { to: "/feed", icon: Home, label: "Главная" },
  { to: "/search", icon: Search, label: "Поиск" },
  { to: "/notifications", icon: Bell, label: "Уведомления", badge: true },
  { to: "/messages", icon: MessageCircle, label: "Сообщения" },
  { to: "/settings", icon: Settings, label: "Настройки" },
];
export function LeftSidebar() {
  const { pathname } = useLocation();
  const { user, isAuthenticated, logout } = useAuthStore();
  const { data: unreadCount } = useUnreadCount();
  return (
    <aside className="hidden md:flex flex-col w-[240px] h-screen sticky top-0 border-r border-border bg-background p-4 gap-2 shrink-0">
      {/* Logo */}
      <Link to="/feed" className="flex items-center gap-2 px-3 py-4 mb-4">
        <motion.div
          whileHover={{ scale: 1.05 }}
          className="h-8 w-8 rounded-lg bg-primary flex items-center justify-center text-primary-foreground font-bold text-sm"
        >
          N
        </motion.div>
        <span className="text-xl font-bold text-foreground">NEXIS</span>
      </Link>
      {/* Nav */}
      <nav className="flex-1 flex flex-col gap-1">
        {navItems.map((item) => {
          const isActive = pathname === item.to;
          return (
            <Link
              key={item.to}
              to={item.to}
              className={cn(
                "flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-all duration-200",
                isActive
                  ? "bg-primary/10 text-primary"
                  : "text-muted-foreground hover:bg-secondary hover:text-foreground"
              )}
            >
              <item.icon className="h-5 w-5" />
              <span>{item.label}</span>
              {item.badge && unreadCount ? (
                <motion.span
                  initial={{ scale: 0 }}
                  animate={{ scale: 1 }}
                  className="ml-auto bg-destructive text-destructive-foreground text-xs rounded-full h-5 min-w-5 flex items-center justify-center px-1"
                >
                  {unreadCount > 99 ? "99+" : unreadCount}
                </motion.span>
              ) : null}
            </Link>
          );
        })}
        {isAuthenticated && user && (
          <Link
            to={`/profile/${user.id}`}
            className={cn(
              "flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-all duration-200",
              pathname.startsWith("/profile")
                ? "bg-primary/10 text-primary"
                : "text-muted-foreground hover:bg-secondary hover:text-foreground"
            )}
          >
            <User className="h-5 w-5" />
            <span>Профиль</span>
          </Link>
        )}
        {isAuthenticated && user?.role === "ADMIN" && (
          <Link
            to="/admin"
            className={cn(
              "flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-all duration-200",
              pathname === "/admin"
                ? "bg-red-500/10 text-red-500"
                : "text-muted-foreground hover:bg-red-500/10 hover:text-red-500"
            )}
          >
            <Shield className="h-5 w-5" />
            <span>Админ панель</span>
          </Link>
        )}
      </nav>
      {/* User card */}
      {isAuthenticated && user && (
        <div className="border-t border-border pt-3 mt-2">
          <div className="flex items-center gap-3 px-2">
            <UserAvatar user={user} size="sm" />
            <div className="flex-1 min-w-0">
              <p className="text-sm font-medium text-foreground truncate">{user.username}</p>
              <p className="text-xs text-muted-foreground truncate">{user.email}</p>
            </div>
            <button
              onClick={logout}
              className="text-muted-foreground hover:text-destructive transition-colors p-1"
              title="Выйти"
            >
              <LogOut className="h-4 w-4" />
            </button>
          </div>
        </div>
      )}
    </aside>
  );
}
