// import 'bootstrap/dist/css/bootstrap.min.css';
import { Route, Routes } from 'react-router-dom';
import './App.css';
import Footer from './components/Footer';
import HeaderHome from './components/HeaderHome';
import HeaderHome2 from './components/HeaderHome2';
import HeaderLogin from './components/HeaderLogin';
import Home from './components/Home';
import Login from './components/Login';
function App() {
  return (
    <div className="App">

      <Routes>
        <Route path='/' element={ <HeaderHome />} />
        <Route path='/login' element={ <HeaderLogin />} />
      </Routes>

      
      <Routes>
        <Route path='/' element={ <HeaderHome2 />} />
      </Routes>

      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login />} />
      </Routes>
      
      <Routes>
       
        <Route path='/login' element={ <Footer />} />
      </Routes>

    </div>
  );
}

export default App;
//https://duthanhduoc.com/blog/p4-luu-jwt-token-o-localstorage-hay-cookie