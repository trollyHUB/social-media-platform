import { Link, useLocation } from "react-router-dom";
import { Home, Search, Bell, MessageCircle, User } from "lucide-react";
import { cn } from "@/lib/utils";
import { useAuthStore } from "@/store/authStore";
import { useUnreadCount } from "@/hooks/useNotifications";
export function BottomNav() {
  const { pathname } = useLocation();
  const { user, isAuthenticated } = useAuthStore();
  const { data: unreadCount } = useUnreadCount();
  const navItems = [
    { to: "/feed", icon: Home, label: "Главная" },
    { to: "/search", icon: Search, label: "Поиск" },
    { to: "/notifications", icon: Bell, label: "Уведомления", badge: true },
    { to: "/messages", icon: MessageCircle, label: "Сообщения" },
    ...(isAuthenticated && user
      ? [{ to: `/profile/${user.id}`, icon: User, label: "Профиль", badge: false }]
      : []),
  ];
  return (
    <nav className="md:hidden fixed bottom-0 left-0 right-0 z-50 bg-background border-t border-border">
      <div className="flex items-center justify-around px-2 py-1.5">
        {navItems.map((item) => {
          const isActive = pathname === item.to || (item.to !== "/" && pathname.startsWith(item.to));
          return (
            <Link
              key={item.to}
              to={item.to}
              className={cn(
                "flex flex-col items-center gap-0.5 px-3 py-1.5 rounded-lg transition-colors relative",
                isActive ? "text-primary" : "text-muted-foreground"
              )}
            >
              <div className="relative">
                <item.icon className="h-5 w-5" />
                {item.badge && unreadCount ? (
                  <span className="absolute -top-1 -right-1 bg-destructive text-destructive-foreground text-[10px] rounded-full h-4 min-w-4 flex items-center justify-center px-0.5">
                    {unreadCount > 9 ? "9+" : unreadCount}
                  </span>
                ) : null}
              </div>
              <span className="text-[10px] font-medium">{item.label}</span>
            </Link>
          );
        })}
      </div>
    </nav>
  );
}
