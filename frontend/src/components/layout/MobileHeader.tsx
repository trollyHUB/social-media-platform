import { Link } from "react-router-dom";
import { Menu } from "lucide-react";

export function MobileHeader() {
  return (
    <header className="md:hidden sticky top-0 z-40 bg-background border-b border-border px-4 py-3 flex items-center justify-between">
      <Link to="/" className="flex items-center gap-2">
        <div className="h-7 w-7 rounded-md bg-primary flex items-center justify-center text-primary-foreground font-bold text-xs">
          N
        </div>
        <span className="text-lg font-bold text-foreground">NEXIS</span>
      </Link>
    </header>
  );
}
