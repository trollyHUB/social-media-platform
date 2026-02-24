import { cn } from "@/lib/utils";
import type { User } from "@/types";

interface UserAvatarProps {
  user?: Pick<User, "username" | "avatarColor" | "avatarUrl"> | null;
  size?: "sm" | "md" | "lg" | "xl";
  className?: string;
}

const sizeMap = {
  sm: "h-8 w-8 text-xs",
  md: "h-10 w-10 text-sm",
  lg: "h-14 w-14 text-lg",
  xl: "h-20 w-20 text-2xl",
};

export function UserAvatar({ user, size = "md", className }: UserAvatarProps) {
  if (user?.avatarUrl) {
    return (
      <img
        src={user.avatarUrl}
        alt={user.username}
        className={cn("rounded-full object-cover shrink-0", sizeMap[size], className)}
      />
    );
  }

  const letter = user?.username?.charAt(0)?.toUpperCase() || "?";
  const color = user?.avatarColor || "#6c63ff";

  return (
    <div
      className={cn(
        "rounded-full flex items-center justify-center font-semibold text-white shrink-0",
        sizeMap[size],
        className
      )}
      style={{ backgroundColor: color }}
    >
      {letter}
    </div>
  );
}
