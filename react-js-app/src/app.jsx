import {Outlet} from "react-router-dom";
import AppLayout from "components/app.layout.jsx";

const App = () => {
    return (
        <>
            <AppLayout>
                <Outlet/>
            </AppLayout>
        </>
    );
}

export default App;
