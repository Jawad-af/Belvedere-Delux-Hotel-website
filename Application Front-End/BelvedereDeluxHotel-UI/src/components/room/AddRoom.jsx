// eslint-disable-next-line no-unused-vars
import React from 'react'
import { useState } from 'react'
import { SendRoomDetails } from '../utils/ApiRequests'
import RoomTypeSelector from '../public/RoomTypeSelector'

// so in order to create a new room we have to create a post request to the backend server
// the request is made using axios which is a HTTP promise-based library for making requests from 
// web browsers environments.

// and we also need a get request in order to fetch the rooms types that are placed in the form 


const AddRoom = () => {

    const [newRoom, setNewRoom] = useState({
        photo: "",
        roomType: "",
        roomPrice: ""
    })

    const[imagePreview, setImagePreview] = useState("")
    const[successMessage, setSuccessMessage] = useState("")
    const[errorMessage, setErrorMessage] = useState("")

    const handleRoomInputChange = (event) => {
        const name = event.target.name
        let value = event.target.value 

        if(name === "roomPrice"){
            if(!isNaN(value)){
                value = parseInt(value)
            }else{
                value = ""
            }
        }
        setNewRoom({...newRoom, [name]: value})
    }

    const handleRoomImageInput = (event) => {
        let image = event.target.files[0]
        setNewRoom({...newRoom, photo: image})
        setImagePreview(URL.createObjectURL(image))
    }


        const handleRoomSubmitInput = async (event) => {
            event.preventDefault()
            const response = await SendRoomDetails(newRoom.photo, newRoom.roomType, newRoom.roomPrice)
            .then(() => {
                setSuccessMessage("A} new room is added successfully!")
                setNewRoom({...newRoom, photo: null, roomType: "", roomPrice: ""})
                setImagePreview("")
            })
            .catch((response) => {
                console.log(response.message)
                setErrorMessage("Error adding the room")
            })
        }
    

  return (
    <>
        <section className="container, mt-5 mb-5">
            <div className='row justify-content-center'>
                <div className='col-md-8 col-lg-6'>
                    <h2 className="mt-5 mb-2">Add New Room</h2>

                    <form onSubmit={handleRoomSubmitInput}>

                        <div className="mb-3">
                            <label className="form-label" htmlFor="roomType">
                                Room Type
                            </label>
                            <div>
                                <RoomTypeSelector 
                                handleRoomInputChange={handleRoomInputChange} 
                                newRoom={newRoom}/>
                            </div>
                        </div>

                        <div className="mb-3">
                            <label className="form-label" htmlFor="roomPrice">
                                Room Price
                            </label>
                            <div>
                                <input className="form-control"
                                id="roomPrice"
                                type="number"
                                name="roomPrice"
                                value={newRoom.roomPrice}
                                onChange={handleRoomInputChange}
                                required 
                                />
                            </div>
                        </div>

                        <div className="mb-3">
                            <label className="form-label" htmlFor="image">
                                Room Image:  
                            </label>
                            <input className="form-control"
                            id="roomImage"
                            name="roomImage"
                            type="file"
                            value={newRoom.photo}
                            onChange={handleRoomImageInput}
                            />
                            {imagePreview && (
                                <img src={imagePreview} 
                                alt="Room Image Preview"
                                style={{maxWidth: "400px", maxHeight: "400px"}}
                                className='mb-3'
                                />
                            )}

                            <div className='d-grid d-md-flex mt-2'>
                                <button className='btn btn-outline-primary ml-5'>Add Room</button>
                            </div>
                        </div>

                    </form>
                </div>
            </div>

        </section>
    </>
  )
}

export default AddRoom