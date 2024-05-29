import { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import * as loginServices from "../../apiServices/loginservices";
import "./headerHome2.css";

function Header() {
  const location = useLocation();
  useEffect(() => {

    const queryParams = new URLSearchParams(location.search);
    const code = queryParams.get('code');
    if (code != null ) {
      loginServices
        .login_github(code)
        .then((viet) => {
          console.log(viet);
        })
        // window.location.href = 'http://localhost:3000'
    }
  },[]);

  return (
    <header className="bg-black clearfix homeRight p-3 ">
      <Link to="/login" className='float-end text-decoration-none'>Đăng nhập</Link>
    </header>
  );
}
export default Header;
