import { useState } from "react";
import { Loader2, LogOut, User } from "lucide-react";
import { motion } from "framer-motion";
import { toast } from "sonner";
import { useAuthStore } from "@/store/authStore";
import { usersApi } from "@/api/users";
import { UserAvatar } from "@/components/UserAvatar";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
export default function SettingsPage() {
  const { user, logout, setUser } = useAuthStore();
  const [bio, setBio] = useState(user?.bio ?? "");
  const [saving, setSaving] = useState(false);
  if (!user) return null;
  const handleSave = async () => {
    setSaving(true);
    try {
      const updated = await usersApi.update(user.id, { bio });
      setUser(updated);
      toast.success("Профиль обновлён");
    } catch {
      toast.error("Ошибка сохранения");
    } finally {
      setSaving(false);
    }
  };
  return (
    <div>
      <div className="sticky top-0 z-30 bg-background/80 backdrop-blur-md border-b border-border px-4 py-3">
        <h1 className="text-lg font-bold text-foreground">Настройки</h1>
      </div>
      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.3 }}
        className="p-6 space-y-6"
      >
        <div className="flex items-center gap-4">
          <UserAvatar user={user} size="xl" />
          <div>
            <h2 className="text-lg font-semibold text-foreground">{user.username}</h2>
            <p className="text-sm text-muted-foreground">{user.email}</p>
          </div>
        </div>
        <div className="space-y-4 max-w-md">
          <div className="space-y-1.5">
            <label className="text-sm text-muted-foreground">О себе</label>
            <Input
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              placeholder="Расскажите о себе..."
              className="bg-secondary border-border"
            />
          </div>
          <Button onClick={handleSave} disabled={saving}>
            {saving ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : null}
            Сохранить
          </Button>
        </div>
        <div className="border-t border-border pt-6">
          <Button variant="destructive" onClick={logout}>
            <LogOut className="h-4 w-4 mr-2" />
            Выйти из аккаунта
          </Button>
        </div>
      </motion.div>
    </div>
  );
}
