import './App.css'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
// import '/node_modules/bootstrap/dist/css/bootstrap.min.css';

import AddRoom from './components/room/AddRoom'
import ExistingRooms from './components/room/ExistingRooms';

function App() {

  return (
    <>
      <AddRoom/>
      {/* <ExistingRooms></ExistingRooms> */}
    </>
  )
}

export default App
