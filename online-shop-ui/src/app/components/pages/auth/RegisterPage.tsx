import * as React from 'react';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import { Alert, Snackbar } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { InputError } from '../../../utils/InputErrorUtils';
import { getTranslation } from '../../../../i18n/i18n';
import { useAppSelector } from '../../../hooks';

import UserInputFields from './UserInputFields';
import * as AuthRequest from '../../../api/AuthRequest';

const RegisterPage = () => {
    const lang = useAppSelector(state => state.lang.lang);
    const [inputErrors, setInputErrors] = React.useState<InputError[]>([]);
    const [isSuccess, setSuccess] = React.useState(false);
    const [error, setError] = React.useState("");

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        setSuccess(false);
        setInputErrors([]);
        setError("");

        const data = new FormData(event.currentTarget);
        const username = data.get('username')?.toString();
        const password = data.get('password')?.toString();
        const role = document.getElementById("role") as HTMLInputElement;
        const email = data.get('email')?.toString();

        const response = await AuthRequest.register(role.innerHTML.toUpperCase() ?? "", email ?? "", username ?? "", password ?? "");

        if (response.error) {
            setError(response.error);
        } else if (response.status == 200) {
            setSuccess(true);
        } else {
            response.fieldErrors && setInputErrors(response.fieldErrors);
        }
    };

    const handleAlertClick = () => {
        setSuccess(false);
        setError("");
    };

    return (
        <Container component="main" maxWidth="xs" sx={{ pt: 26 }}>
            <Grid container>
                <Grid item>
                    {isSuccess &&
                        <Snackbar open={isSuccess} autoHideDuration={2000} onClose={handleAlertClick}>
                            <Alert data-testid="alert-success" id="alert-success" onClose={handleAlertClick} severity="success" sx={{ width: '100%' }} >
                                {getTranslation(lang, "user_registered_successfully")}
                            </Alert>
                        </Snackbar>}
                    {error.length > 0 &&
                        <Snackbar open={error.length > 0} autoHideDuration={2000} onClose={handleAlertClick}>
                            <Alert data-testid="alert-error" id="alert-error" onClose={handleAlertClick} severity="error" sx={{ width: '100%' }}>
                                {getTranslation(lang, error)}
                            </Alert>
                        </Snackbar>}
                    <UserInputFields title={getTranslation(lang, "sign_up")} buttonText={getTranslation(lang, "sign_up")} handleSubmit={handleSubmit} isEmailAndRoleMandatory={true} inputErrors={inputErrors} />
                </Grid>
                <Grid item>
                    <Link component={RouterLink} to={'/login'} variant="body2" id="have-already-account">
                        {getTranslation(lang, "have_account_message")}
                    </Link>
                </Grid>
            </Grid>
        </Container>
    );
}

export default RegisterPage;
