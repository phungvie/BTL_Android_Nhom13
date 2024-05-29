import { Link } from "react-router-dom";
import "./headerHome.css";

function HeaderHome() {


  return (
    <header className=" headerHome ">
      <ul className="bg-black rounded-3 p-3 mx-1">
        <li>
          <a href="/"><h1>Sonic</h1></a>
        </li>
        <li>
          <Link to="/">Trang chủ</Link>
        </li>
        <li>
          <Link to="/Seach">Tìm kiểm</Link>
        </li>
      </ul>

      <Link to="/Lib">Thư viện</Link>
    </header>
  );
}
export default HeaderHome;
