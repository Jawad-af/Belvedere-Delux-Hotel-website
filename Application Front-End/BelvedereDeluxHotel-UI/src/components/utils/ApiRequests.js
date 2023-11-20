import axios from 'axios'

const api = axios.create(
    {
        BaseURL: 'http://localhost:8088'
    }
)

export async function SendRoomDetails(photo, roomType, roomPrice) {
    
    const formData = new FormData()
    formData.append("photo", photo)
    formData.append("roomType", roomType)
    formData.append("roomPrice", roomPrice)

    const response = await axios.post('http://localhost:8088/rooms/add/new-room', formData)

    if(response == 201){
        return true
    } else {
        return false
    }
}

export async function GetRoomTypes() {

    try{
        const response = await axios.get('http://localhost:8088/rooms/room-types')
        return response.data
    }catch(error){
        throw new error("Error fetching rooms types")
    }
}