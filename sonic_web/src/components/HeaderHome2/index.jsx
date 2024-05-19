import { Link } from 'react-router-dom';
import "./headerHome2.css";
function Header() {
  return (
    <header className="bg-black clearfix homeRight p-3 ">
      <Link to="/login" className='float-end text-decoration-none'>Đăng nhập</Link>
    </header>
  );
}
export default Header;
