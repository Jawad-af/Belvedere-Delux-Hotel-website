import axios from 'axios'

const api = axios.create(
    {
        baseURL: 'http://localhost:8088'
    }
)

export async function addRoom(photo, roomType, roomPrice) {
    
    const formData = new FormData()
    formData.append("photo", photo)
    formData.append("roomType", roomType)
    formData.append("roomPrice", roomPrice)

    const response = await api.post('http://localhost:8088/rooms/add/new-room', formData)

    if(response.status == 201){
        return true
    } else {
        return false
    }
}

export async function getRoomTypes() {

    try{
        const response = await axios.get("http://localhost:8088/rooms/room-types")
        return response.data
    }catch(error){
        throw new error("Error fetching rooms types")
    }
}