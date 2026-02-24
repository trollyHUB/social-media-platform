import { create } from "zustand";
import { persist } from "zustand/middleware";
import type { User } from "@/types";
import { authApi } from "@/api/auth";

interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (username: string, email: string, password: string, bio?: string) => Promise<void>;
  logout: () => void;
  setUser: (user: User) => void;
  initialize: () => Promise<void>;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      isLoading: true,

      login: async (email, password) => {
        const res = await authApi.login({ email, password });
        localStorage.setItem("nexis-token", res.token);
        set({ user: res.user, token: res.token, isAuthenticated: true });
      },

      register: async (username, email, password, bio) => {
        const res = await authApi.register({ username, email, password, bio });
        localStorage.setItem("nexis-token", res.token);
        set({ user: res.user, token: res.token, isAuthenticated: true });
      },

      logout: () => {
        localStorage.removeItem("nexis-token");
        set({ user: null, token: null, isAuthenticated: false });
      },

      setUser: (user) => set({ user }),

      initialize: async () => {
        const token = localStorage.getItem("nexis-token");
        if (!token) {
          set({ isLoading: false });
          return;
        }
        try {
          const user = await authApi.me();
          set({ user, token, isAuthenticated: true, isLoading: false });
        } catch {
          localStorage.removeItem("nexis-token");
          set({ user: null, token: null, isAuthenticated: false, isLoading: false });
        }
      },
    }),
    {
      name: "nexis-auth",
      partialize: (state) => ({ token: state.token }),
    }
  )
);
