import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "@/store/authStore";
import { Loader2 } from "lucide-react";

function LoadingScreen() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-background">
      <div className="flex flex-col items-center gap-3">
        <div className="h-10 w-10 rounded-xl bg-primary flex items-center justify-center text-primary-foreground font-black">
          N
        </div>
        <Loader2 className="h-5 w-5 animate-spin text-primary" />
      </div>
    </div>
  );
}

// PublicRoute: для незалогиненных. Если залогинен — редирект на redirect (default /feed)
export function PublicRoute({
  children,
  redirect = "/feed",
}: {
  children: React.ReactNode;
  redirect?: string;
}) {
  const { isAuthenticated, isLoading } = useAuthStore();
  if (isLoading) return <LoadingScreen />;
  return isAuthenticated ? <Navigate to={redirect} replace /> : <>{children}</>;
}

// PrivateRoute: для залогиненных. Если нет — на /login
// Используется двумя способами: с children (оборачивает Layout) или без (Outlet)
export function PrivateRoute({ children }: { children?: React.ReactNode }) {
  const { isAuthenticated, isLoading } = useAuthStore();
  if (isLoading) return <LoadingScreen />;
  if (!isAuthenticated) return <Navigate to="/login" replace />;
  return children ? <>{children}</> : <Outlet />;
}
