import React from 'react';
import { Typography, useTheme } from '@mui/material';
import Grid from '@mui/material/Grid';
import { getTranslation } from '../../../i18n/i18n';
import { useAppSelector } from '../../hooks';

const Copyright = (props: any) => {
    const lang = useAppSelector(state => state.lang.lang);
    const theme = useTheme();

    return (
        <Grid container direction="row" style={{ marginTop: "50px" }} sx={{ mt: "4%", p: "1%", bottom: 0, top: "100vh", position: "sticky" }} bgcolor={theme.palette.mode === "light" ? "#1976d2" : "#272727"} data-testid="footer">
            <Grid item width="34%" ml="16%" sx={{display: 'flex', flexDirection: "column", justifyContent: "space-between"}}>
                <Typography variant="h5" color="#FFF" align="left" sx={{ lineHeight: "1", fontWeight: 600 }}>{getTranslation(lang, "online_shop")}</Typography>
                <Typography variant="subtitle2" color="#FFF" align="left" sx={{ lineHeight: "1" }}>skprphodit@shop.com<br />+40 336 772 413</Typography>
                <Typography variant="subtitle2" color="#FFF" align="right" sx={{ lineHeight: "1" }} marginTop="4%">
                    © {new Date().getFullYear()}
                    <a href="https://github.com/MtTarik" target="_blank" rel="noopener noreferrer" style={{ color: '#FFF', marginLeft: '5px' }}>MtTarik</a> {/* Посилання на тії GitHub */}
                </Typography>            </Grid>
            <Grid item width="34%" mr="16%">
                <Typography variant="subtitle2" color="#FFF" align="right" sx={{ lineHeight: "1", textAlign: "justify" }}>ВІтаюMtTarik.</Typography>
                <Typography variant="subtitle2" color="#FFF" align="right" sx={{ lineHeight: "1" }} marginTop="4%">
                    © {new Date().getFullYear()}
                    <a href="https://github.com/MtTarik" target="_blank" rel="noopener noreferrer" style={{ color: '#FFF', marginLeft: '5px' }}>MtTarik</a> {/* Посилання на тії GitHub */}
                </Typography>
            </Grid>
        </Grid>
    );
}

export default Copyright;
