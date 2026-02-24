import { Bell, Heart, MessageCircle, UserPlus } from "lucide-react";
import { formatDistanceToNow } from "date-fns";
import { ru } from "date-fns/locale";
import { motion, AnimatePresence } from "framer-motion";
import { cn } from "@/lib/utils";
import { useNotifications, useMarkAllRead, useMarkRead } from "@/hooks/useNotifications";
import { NotificationType } from "@/types";
import { Button } from "@/components/ui/button";
const iconMap: Record<NotificationType, React.ElementType> = {
  [NotificationType.LIKE]: Heart,
  [NotificationType.COMMENT]: MessageCircle,
  [NotificationType.FOLLOW]: UserPlus,
  [NotificationType.MENTION]: MessageCircle,
};
export default function NotificationsPage() {
  const { data: notifications, isLoading } = useNotifications();
  const markAll = useMarkAllRead();
  const markOne = useMarkRead();
  return (
    <div>
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3 flex items-center justify-between">
        <h1 className="text-lg font-bold text-foreground">Уведомления</h1>
        {notifications && notifications.some((n) => !n.isRead) && (
          <Button variant="ghost" size="sm" onClick={() => markAll.mutate()}>
            Прочитать все
          </Button>
        )}
      </div>
      {isLoading ? (
        <div className="py-20 text-center text-muted-foreground text-sm">Загрузка...</div>
      ) : notifications && notifications.length > 0 ? (
        <AnimatePresence>
          {notifications.map((n, i) => {
            const Icon = iconMap[n.type] ?? Bell;
            return (
              <motion.div
                key={n.id}
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: i * 0.04, duration: 0.25 }}
                onClick={() => !n.isRead && markOne.mutate(n.id)}
                className={cn(
                  "flex items-start gap-3 p-4 border-b border-border transition-colors cursor-pointer hover:bg-secondary/30",
                  !n.isRead && "bg-primary/5"
                )}
              >
                <Icon
                  className={cn(
                    "h-5 w-5 mt-0.5 shrink-0",
                    n.type === NotificationType.LIKE ? "text-destructive" : "text-primary"
                  )}
                />
                <div className="flex-1 min-w-0">
                  <p className="text-sm text-foreground">{n.message}</p>
                  <p className="text-xs text-muted-foreground mt-1">
                    {formatDistanceToNow(new Date(n.createdAt), { addSuffix: true, locale: ru })}
                  </p>
                </div>
                {!n.isRead && <div className="h-2 w-2 rounded-full bg-primary mt-2 shrink-0" />}
              </motion.div>
            );
          })}
        </AnimatePresence>
      ) : (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="py-20 text-center text-muted-foreground text-sm"
        >
          <Bell className="h-12 w-12 mx-auto mb-3 opacity-50" />
          Нет уведомлений
        </motion.div>
      )}
    </div>
  );
}
