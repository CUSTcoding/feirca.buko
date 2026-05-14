import HomeLayout from "@/components/layout/home/home";
import Sidebar from "@/components/layout/siderbar";

export default function Page() {
  return (
    <main className="h-screen w-screen flex">
      <Sidebar />
      <HomeLayout/>
    </main>
  )
}



