import { BrowserRouter, Route, Routes, useLocation, useNavigate } from "react-router-dom";
import IndexPage from "./pages/IndexPage/IndexPage";
import UserJoinPage from "./pages/UserJoinPage/UserJoinPage";
import UserLoginPage from "./pages/UserLoginPage/UserLoginPage";
import { instance } from "./apis/util/instance";
import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import UserProfilePage from "./pages/UserProfilePage/UserProfilePage";

function App() {
    const location = useLocation();
    const navigate = useNavigate();
    const [ authRefresh, setAuthRefresh ] = useState(true);

    useEffect(() => {
        if(!authRefresh){
            setAuthRefresh(true);
        }
    }, [location.pathname]);

    const accessTokenValid = useQuery(
        ["accessTokenValidQuery"],
        async () => {
            setAuthRefresh(false);
            return await instance.get("/auth/access", {
                params: {
                    accessToken: localStorage.getItem("accessToken")
                }
            });
        }, {
        enabled: authRefresh,
        retry: 0,
        refetchOnWindowFocus: false,
        onSuccess: response => {
            const permitAllPaths = ["/user"];
            for(let permitAllPath of permitAllPaths) {
                if(location.pathname.startsWith(permitAllPath)){
                    // alert("잘못된 요청입니다.");
                    navigate("/");
                    break;
                }
            }
        },
        onError: error => {
            const authPaths = ["/profile"];
            for (let authPath of authPaths) {
                if (location.pathname.startsWith(authPath)) {
                    navigate("/user/login");
                    break;
                }
            }
        }
    }
    );

    const userInfo = useQuery(
        ["userInfoQuery"],
        async () => {
            return await instance.get("/user/me");
        },
        {
            enabled: accessTokenValid.isSuccess && accessTokenValid.data?.data,
            retry: 0,
            refetchOnWindowFocus: false,
        },
    )

    return (
        <Routes>
            <Route path="/" element={<IndexPage />} />
            <Route path="/user/join" element={<UserJoinPage />} />
            <Route path="/user/login" element={<UserLoginPage />} />
            <Route path="/profile" element={<UserProfilePage />} />
            <Route path="/admin/*" element={<></>} />
            <Route path="/admin/*" element={<h1>Not Found</h1>} />
            <Route path="*" element={<h1>Not Found</h1>} />
        </Routes>
    );
}

export default App;
