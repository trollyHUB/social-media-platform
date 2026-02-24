import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { ArrowRight, Zap, Shield, Globe, MessageSquare, Heart, Users, TrendingUp, Star, CheckCircle } from "lucide-react";
import { Button } from "@/components/ui/button";
const features = [
  { icon: MessageSquare, title: "Живые обсуждения", desc: "Делись мыслями, общайся в реальном времени. Твой голос слышат тысячи." },
  { icon: Heart, title: "Умная лента", desc: "Алгоритм показывает только то, что тебе важно. Никакого информационного мусора." },
  { icon: Users, title: "Сообщества", desc: "Находи людей со схожими интересами. Создавай комьюнити вокруг своих идей." },
  { icon: Shield, title: "Приватность", desc: "Твои данные — только твои. Полный контроль над тем, кто тебя видит." },
  { icon: Globe, title: "Без границ", desc: "Платформа доступна 24/7. Общайся с людьми по всему миру." },
  { icon: TrendingUp, title: "Аналитика", desc: "Отслеживай рост своей аудитории. Понимай, что резонирует с людьми." },
];
const stats = [
  { value: "10K+", label: "Активных пользователей" },
  { value: "50K+", label: "Постов в день" },
  { value: "99.9%", label: "Uptime" },
  { value: "< 100ms", label: "Скорость ответа" },
];
const testimonials = [
  { name: "Алихан М.", role: "Разработчик", text: "NEXIS — это то, что я искал. Чистый интерфейс, быстро, без рекламы.", avatar: "А" },
  { name: "Дана С.", role: "Дизайнер", text: "Наконец соцсеть, где важен контент, а не алгоритмы для монетизации.", avatar: "Д" },
  { name: "Марат К.", role: "Студент", text: "Отличная платформа для студентов. Нашёл здесь ментора за 3 дня!", avatar: "М" },
];
export default function LandingPage() {
  return (
    <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
      {/* NAV */}
      <nav className="fixed top-0 left-0 right-0 z-50 bg-background/80 backdrop-blur-xl border-b border-border/50">
        <div className="max-w-6xl mx-auto px-4 h-16 flex items-center justify-between">
          <motion.div initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="flex items-center gap-2">
            <div className="h-8 w-8 rounded-lg bg-primary flex items-center justify-center text-primary-foreground font-black text-sm">N</div>
            <span className="text-xl font-black text-foreground tracking-tight">NEXIS</span>
          </motion.div>
          <motion.div initial={{ opacity: 0, x: 20 }} animate={{ opacity: 1, x: 0 }} className="flex items-center gap-3">
            <Link to="/login">
              <Button variant="ghost" size="sm">Войти</Button>
            </Link>
            <Link to="/register">
              <Button size="sm" className="font-semibold">Начать бесплатно</Button>
            </Link>
          </motion.div>
        </div>
      </nav>
      {/* HERO */}
      <section className="relative pt-32 pb-24 px-4 overflow-hidden">
        {/* Gradient orbs */}
        <div className="absolute top-20 left-1/4 w-96 h-96 bg-primary/20 rounded-full blur-3xl pointer-events-none" />
        <div className="absolute top-40 right-1/4 w-64 h-64 bg-purple-500/10 rounded-full blur-3xl pointer-events-none" />
        <div className="max-w-4xl mx-auto text-center relative">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
            className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-primary/10 border border-primary/20 text-primary text-xs font-semibold mb-6"
          >
            <Zap className="h-3 w-3" />
            Новое поколение социальных сетей
          </motion.div>
          <motion.h1
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, delay: 0.1 }}
            className="text-5xl md:text-7xl font-black text-foreground mb-6 leading-tight tracking-tight"
          >
            Общайся.
            <span className="text-primary block">Создавай.</span>
            Вдохновляй.
          </motion.h1>
          <motion.p
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.2 }}
            className="text-lg md:text-xl text-muted-foreground mb-10 max-w-2xl mx-auto leading-relaxed"
          >
            NEXIS Connect — платформа нового поколения. Без рекламы, без манипуляций.
            Только ты, твои мысли и люди, которым это важно.
          </motion.p>
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.3 }}
            className="flex flex-col sm:flex-row items-center justify-center gap-4"
          >
            <Link to="/register">
              <Button size="lg" className="text-base font-bold px-8 gap-2 h-12">
                Создать аккаунт бесплатно
                <ArrowRight className="h-4 w-4" />
              </Button>
            </Link>
            <Link to="/login">
              <Button size="lg" variant="outline" className="text-base font-semibold px-8 h-12">
                Уже есть аккаунт
              </Button>
            </Link>
          </motion.div>
          <motion.p
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.5 }}
            className="text-xs text-muted-foreground mt-4 flex items-center justify-center gap-2"
          >
            <CheckCircle className="h-3 w-3 text-green-500" />
            Бесплатно навсегда. Без кредитной карты.
          </motion.p>
        </div>
      </section>
      {/* STATS */}
      <section className="py-12 border-y border-border bg-card/30">
        <div className="max-w-4xl mx-auto px-4 grid grid-cols-2 md:grid-cols-4 gap-8">
          {stats.map((s, i) => (
            <motion.div
              key={s.label}
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ delay: i * 0.1 }}
              className="text-center"
            >
              <div className="text-3xl md:text-4xl font-black text-primary">{s.value}</div>
              <div className="text-sm text-muted-foreground mt-1">{s.label}</div>
            </motion.div>
          ))}
        </div>
      </section>
      {/* FEATURES */}
      <section className="py-24 px-4">
        <div className="max-w-5xl mx-auto">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            className="text-center mb-16"
          >
            <h2 className="text-3xl md:text-5xl font-black text-foreground mb-4">
              Всё что нужно для <span className="text-primary">настоящего</span> общения
            </h2>
            <p className="text-muted-foreground text-lg">Построено с заботой о пользователе, а не о рекламодателях</p>
          </motion.div>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {features.map((f, i) => (
              <motion.div
                key={f.title}
                initial={{ opacity: 0, y: 30 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ delay: i * 0.08 }}
                whileHover={{ y: -4 }}
                className="p-6 rounded-2xl border border-border bg-card hover:border-primary/30 hover:bg-card/80 transition-all duration-300 group"
              >
                <div className="h-10 w-10 rounded-xl bg-primary/10 flex items-center justify-center mb-4 group-hover:bg-primary/20 transition-colors">
                  <f.icon className="h-5 w-5 text-primary" />
                </div>
                <h3 className="font-bold text-foreground mb-2">{f.title}</h3>
                <p className="text-sm text-muted-foreground leading-relaxed">{f.desc}</p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>
      {/* TESTIMONIALS */}
      <section className="py-24 px-4 bg-card/20">
        <div className="max-w-4xl mx-auto">
          <motion.h2
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            className="text-3xl md:text-4xl font-black text-center mb-12"
          >
            Что говорят <span className="text-primary">пользователи</span>
          </motion.h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {testimonials.map((t, i) => (
              <motion.div
                key={t.name}
                initial={{ opacity: 0, y: 20 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ delay: i * 0.1 }}
                className="p-6 rounded-2xl border border-border bg-card"
              >
                <div className="flex items-center gap-1 mb-4">
                  {Array.from({ length: 5 }).map((_, j) => (
                    <Star key={j} className="h-4 w-4 fill-yellow-500 text-yellow-500" />
                  ))}
                </div>
                <p className="text-sm text-muted-foreground mb-4 leading-relaxed">"{t.text}"</p>
                <div className="flex items-center gap-3">
                  <div className="h-9 w-9 rounded-full bg-primary/20 flex items-center justify-center text-primary font-bold text-sm">{t.avatar}</div>
                  <div>
                    <div className="text-sm font-semibold text-foreground">{t.name}</div>
                    <div className="text-xs text-muted-foreground">{t.role}</div>
                  </div>
                </div>
              </motion.div>
            ))}
          </div>
        </div>
      </section>
      {/* CTA */}
      <section className="py-24 px-4">
        <div className="max-w-2xl mx-auto text-center">
          <motion.div
            initial={{ opacity: 0, scale: 0.95 }}
            whileInView={{ opacity: 1, scale: 1 }}
            viewport={{ once: true }}
            className="p-10 rounded-3xl border border-primary/20 bg-gradient-to-b from-primary/10 to-transparent relative overflow-hidden"
          >
            <div className="absolute inset-0 bg-primary/5 rounded-3xl" />
            <div className="relative">
              <h2 className="text-3xl md:text-4xl font-black text-foreground mb-4">
                Готов присоединиться?
              </h2>
              <p className="text-muted-foreground mb-8">
                Тысячи людей уже общаются на NEXIS. Твоя очередь.
              </p>
              <Link to="/register">
                <Button size="lg" className="text-base font-bold px-10 gap-2 h-12">
                  Присоединиться бесплатно
                  <ArrowRight className="h-4 w-4" />
                </Button>
              </Link>
            </div>
          </motion.div>
        </div>
      </section>
      {/* FOOTER */}
      <footer className="border-t border-border py-8 px-4">
        <div className="max-w-4xl mx-auto flex flex-col md:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-2">
            <div className="h-6 w-6 rounded-md bg-primary flex items-center justify-center text-primary-foreground font-black text-xs">N</div>
            <span className="font-bold text-foreground">NEXIS Connect</span>
          </div>
          <p className="text-xs text-muted-foreground">© 2026 NEXIS. Создано с заботой о людях.</p>
          <div className="flex gap-4 text-xs text-muted-foreground">
            <Link to="/login" className="hover:text-foreground transition-colors">Войти</Link>
            <Link to="/register" className="hover:text-foreground transition-colors">Регистрация</Link>
          </div>
        </div>
      </footer>
    </div>
  );
}