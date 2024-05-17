import { Link } from 'react-router-dom';
import "./headerHome.css";
function HeaderHome() {
  return (
    <header className="bg-black headerHome">
     <ul>
        <li>
            <Link to="/" >Trang chủ</Link>
        </li>
        <li>
            <Link to="/Seach" >Tìm kiểm</Link>
        </li>
        <li>
            <Link to="/Lib"  >Thư viện</Link>
        </li>
     </ul>
    </header>
  );
}
export default HeaderHome;