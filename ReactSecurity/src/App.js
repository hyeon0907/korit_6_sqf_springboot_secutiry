import { BrowserRouter, Route, Routes } from "react-router-dom";
import IndexPage from "./pages/indexPage/IndexPage";
import UserJoinPage from "./pages/UserJoinPage/UserJoinPage";
import UserLoginPage from "./pages/UserLoginPage/UserLoginPage";
import { instance } from "./apis/util/instance";
import { useEffect, useState } from "react";
import { useQuery } from "react-query";

function App() {
    const [ refresh, setRefresh ] = useState(false);

    const accessTokenValid = useQuery(
        ["accessTokenValidQuery"], 
        async () => {
            setRefresh(false);
            return await instance.get("/auth/access", {
                params: {
                    accessToken: localStorage.getItem("accessToken")
                }
            });
        }, {
            retry: 0,
            onSuccess: response => {
                console.log("OK 응답 !!!", response.data);
            },
            onError: error => {
                console.log("오류!!!", error);
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
            onSuccess: response => {
                console.log("onSuccess: ", response);
            },
            onError: error => {
                console.log("onError: ", error);
            }
        },
    )

    useEffect(() => {
        // const accessToken = localStorage.getItem("accessToken");
        // if(!!accessToken) {
        //     setRefresh(true)
        // }
        console.log("Effect!!!");
    }, [accessTokenValid])

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={ <IndexPage /> }/>
                <Route path="/user/join" element={ <UserJoinPage /> }/>
                <Route path="/user/login" element={ <UserLoginPage /> }/>

                <Route path="/admin/*" element={ <></> }/>
                <Route path="/admin/*" element={ <h1>Not Found</h1> }/>
                <Route path="*" element={ <h1>Not Found</h1> }/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
