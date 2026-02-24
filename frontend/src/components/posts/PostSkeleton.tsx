import { cn } from "@/lib/utils";

export function PostSkeleton() {
  return (
    <div className="p-4 border-b border-border animate-pulse">
      <div className="flex gap-3">
        <div className="h-10 w-10 rounded-full bg-secondary shrink-0" />
        <div className="flex-1 space-y-2">
          <div className="flex gap-2">
            <div className="h-4 w-24 rounded bg-secondary" />
            <div className="h-4 w-16 rounded bg-secondary" />
          </div>
          <div className="h-4 w-full rounded bg-secondary" />
          <div className="h-4 w-3/4 rounded bg-secondary" />
          <div className="flex gap-6 pt-2">
            <div className="h-4 w-12 rounded bg-secondary" />
            <div className="h-4 w-12 rounded bg-secondary" />
            <div className="h-4 w-12 rounded bg-secondary" />
          </div>
        </div>
      </div>
    </div>
  );
}
