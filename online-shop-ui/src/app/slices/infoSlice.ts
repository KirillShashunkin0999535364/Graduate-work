import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import type { RootState } from '../../store/store'

interface UserInfoType {
    roles: Array<{
        id: number;
        name: string;
    }>,
    email: string,
    firstName: string,
    lastName: string,
    address: string,
    phoneNumber: string
}

const initialState = {
    info: {
        roles: [{ id: 0, name: "null" }],
        email: "",
        firstName: "",
        lastName: "",
        address: "",
        phoneNumber: ""
    }
}

export const infoSlice = createSlice({
    name: 'info',
    initialState,
    reducers: {
        updateInfo: (state, action: PayloadAction<UserInfoType>) => {
            state.info = action.payload;
        }
    }
})

export const { updateInfo } = infoSlice.actions

export const getInfo = (state: RootState) => state;

export default infoSlice.reducer;
