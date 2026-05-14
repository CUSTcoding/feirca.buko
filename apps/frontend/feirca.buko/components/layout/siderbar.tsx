import Navbar from "../ui/nav";
import Profile from "../ui/profile";

export default  function Sidebar(){



    return(
        <aside className="w-[calc(100%/8)] h-screen  flex flex-col justify-around">
            <Profile/>
            <Navbar/>
        </aside>
    );
}