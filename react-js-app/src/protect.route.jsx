import {useContext, useEffect, useState} from "react";
import {Navigate, Outlet} from "react-router-dom";

import {AppContext} from "@/app.context.jsx";
import {RefreshAccountAPI} from "services/axios.api.service.js";
import {PacmanLoader} from "react-spinners";

const ProtectedRoute = () => {
    const {profile, setProfile} = useContext(AppContext);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const handleTryToFetchAccount = async () => {
            const token = localStorage.getItem("access_token");
            if (token) {
                const responseData = await RefreshAccountAPI();
                if (responseData) {
                    setProfile(responseData);
                }
            }

            setIsLoading(false);
        }

        handleTryToFetchAccount();
    },[])

    if(isLoading) {
        return (
            <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
                <PacmanLoader size={30} color="#0E2E6E" />
            </div>
        );
    }

    return (profile) ? (<Outlet/>) : (<Navigate to="/login" replace/>);
}

export default ProtectedRoute;