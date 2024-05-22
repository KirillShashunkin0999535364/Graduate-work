

import React from 'react';
import './../main.scss';

import Main from './Main';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { useAppSelector } from './hooks';

function App() {
    const theme = useAppSelector(state => state.theme.theme);

    const lightTheme = createTheme({
        palette: {
            mode: 'light',
        },
    });

    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
        },
    });

    return (
        <ThemeProvider theme={theme ? lightTheme : darkTheme}>
            <CssBaseline />
            <Main />
        </ThemeProvider>
    );
}

export default App;
