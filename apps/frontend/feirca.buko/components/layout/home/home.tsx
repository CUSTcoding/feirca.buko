import Header from "@/components/ui/header";

export default function HomeLayout(){
    return(
        <main className="flex flex-col pt-17 flex-1  items-center justify-center h-screen">
            <Header/>
            <div className="content flex-1 w-full h-full bg-red-800"></div>

        </main>
    );
}