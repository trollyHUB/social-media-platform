import { Outlet } from "react-router-dom";
import { LeftSidebar } from "./LeftSidebar";
import { RightSidebar } from "./RightSidebar";
import { BottomNav } from "./BottomNav";
import { MobileHeader } from "./MobileHeader";

export function Layout() {
  return (
    <div className="min-h-screen bg-background flex flex-col">
      <MobileHeader />
      <div className="flex flex-1 max-w-[1200px] w-full mx-auto">
        <LeftSidebar />
        <main className="flex-1 min-w-0 border-r border-border md:max-w-[600px]">
          <Outlet />
        </main>
        <RightSidebar />
      </div>
      <BottomNav />
    </div>
  );
}
