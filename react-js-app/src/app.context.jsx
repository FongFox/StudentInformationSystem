import {createContext, useContext, useState} from "react";

export const AppContext = createContext({
    profile: null,
    setProfile: () => {
    }
});

export const AppProvider = (props) => {
    const [profile, setProfile] = useState(null);

    return (
        <AppContext.Provider value={{
            profile, setProfile
        }}>
            {/* eslint-disable-next-line react/prop-types */}
            {props.children}
        </AppContext.Provider>
    );
}

export const useApp = () => {
    const context = useContext(AppContext);

    if (!context) {
        throw new Error('useApp must be used within an AppProvider');
    }

    return context;
}