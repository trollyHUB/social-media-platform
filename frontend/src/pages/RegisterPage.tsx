import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Eye, EyeOff, Loader2 } from "lucide-react";
import { motion } from "framer-motion";
import { toast } from "sonner";
import { useAuthStore } from "@/store/authStore";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
interface FormState {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  bio: string;
}
export default function RegisterPage() {
  const [form, setForm] = useState<FormState>({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
    bio: "",
  });
  const [showPw, setShowPw] = useState(false);
  const [loading, setLoading] = useState(false);
  const register = useAuthStore((s) => s.register);
  const navigate = useNavigate();
  const set = (field: keyof FormState) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (form.password !== form.confirmPassword) {
      toast.error("Пароли не совпадают");
      return;
    }
    if (form.password.length < 6) {
      toast.error("Пароль минимум 6 символов");
      return;
    }
    setLoading(true);
    try {
      await register(form.username, form.email, form.password, form.bio || undefined);
      toast.success("Регистрация успешна!");
      navigate("/");
    } catch (err: any) {
      toast.error(err?.response?.data?.message || "Ошибка регистрации");
    } finally {
      setLoading(false);
    }
  };
  return (
    <div className="min-h-screen flex items-center justify-center bg-background p-4">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.4 }}
        className="w-full max-w-sm space-y-6"
      >
        <div className="text-center space-y-2">
          <motion.div
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ delay: 0.1, duration: 0.3 }}
            className="h-12 w-12 rounded-xl bg-primary flex items-center justify-center text-primary-foreground font-bold text-xl mx-auto"
          >
            N
          </motion.div>
          <h1 className="text-2xl font-bold text-foreground">Регистрация</h1>
          <p className="text-sm text-muted-foreground">Создайте аккаунт NEXIS</p>
        </div>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-1.5">
            <label className="text-sm text-muted-foreground">Имя пользователя</label>
            <Input
              value={form.username}
              onChange={set("username")}
              placeholder="username"
              className="bg-secondary border-border"
              required
            />
          </div>
          <div className="space-y-1.5">
            <label className="text-sm text-muted-foreground">Email</label>
            <Input
              type="email"
              value={form.email}
              onChange={set("email")}
              placeholder="you@example.com"
              className="bg-secondary border-border"
              required
            />
          </div>
          <div className="space-y-1.5">
            <label className="text-sm text-muted-foreground">Пароль</label>
            <div className="relative">
              <Input
                type={showPw ? "text" : "password"}
                value={form.password}
                onChange={set("password")}
                placeholder="••••••••"
                className="bg-secondary border-border pr-10"
                required
              />
              <button
                type="button"
                onClick={() => setShowPw(!showPw)}
                className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground"
              >
                {showPw ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
              </button>
            </div>
          </div>
          <div className="space-y-1.5">
            <label className="text-sm text-muted-foreground">Подтвердите пароль</label>
            <Input
              type="password"
              value={form.confirmPassword}
              onChange={set("confirmPassword")}
              placeholder="••••••••"
              className="bg-secondary border-border"
              required
            />
          </div>
          <div className="space-y-1.5">
            <label className="text-sm text-muted-foreground">О себе (необязательно)</label>
            <Input
              value={form.bio}
              onChange={set("bio")}
              placeholder="Расскажите о себе"
              className="bg-secondary border-border"
            />
          </div>
          <Button type="submit" className="w-full" disabled={loading}>
            {loading ? <Loader2 className="h-4 w-4 animate-spin" /> : "Зарегистрироваться"}
          </Button>
        </form>
        <p className="text-center text-sm text-muted-foreground">
          Уже есть аккаунт?{" "}
          <Link to="/login" className="text-primary hover:underline">
            Войти
          </Link>
        </p>
      </motion.div>
    </div>
  );
}
