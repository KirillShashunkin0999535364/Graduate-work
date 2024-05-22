
import React from 'react';
import Typography from '@mui/material/Typography';
import { useAppSelector } from '../../../hooks';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import { Box } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import { addProductToCart, getProductPhoto } from '../../../api/ProductRequest';
import { getTranslation } from '../../../../i18n/i18n';
import NoPhoto from "../../../../img/no_photo.jpg";
import { useNavigate } from 'react-router-dom';

interface ISearchHit {
    objectID: number,
    name: string,
    price: number
}

const SearchHit = ({ objectID, name, price }: ISearchHit) => {
    const [isSuccess, setIsSuccess] = React.useState<boolean>(false);
    const [photo, setPhoto] = React.useState(NoPhoto);
    const lang = useAppSelector(state => state.lang.lang);
    const jwt = useAppSelector(state => state.jwt);
    const navigate = useNavigate();

    const handleAlertClick = () => {
        setIsSuccess(false);
    };

    const handleAddProduct = async () => {
        setIsSuccess(false);

        const response = await addProductToCart(jwt.token, objectID, 1);

        if (response.status) {
            if (response.status == 401) {
                navigate("/login");
            }
        }

        if (response.status == 200) {
            setIsSuccess(true);
        }
    }

    React.useEffect(() => {
        const getProductPhotoRequest = async () => {
            const photoBlob = await getProductPhoto(objectID);

            if (photoBlob.size > 0) {
                setPhoto(URL.createObjectURL(photoBlob));
            }
        }

        getProductPhotoRequest();
    }, []);

    return (<>
        {isSuccess &&
            <Snackbar open={isSuccess} autoHideDuration={2000} onClose={handleAlertClick}>
                <Alert onClose={handleAlertClick} id="alert-success" severity="success" sx={{ width: '100%' }}>
                    {getTranslation(lang, "product_added_successfully")}
                </Alert>
            </Snackbar>}
        <Box sx={{ display: "flex", flexDirection: "row", justifyContent: "space-between", alignItems: "center", width: "100%" }}>
            <Box height={"5vh"} sx={{ aspectRatio: "1/1" }}>
                <img width={"100%"} height={"100%"} className="card-image" src={photo} alt="product-image" data-testid={"card-image-" + objectID} />
            </Box>
            <Typography variant="h6" sx={{ fontWeight: 200, width: "35%", ml: "1.5%", overflow: "hidden", lineHeight: "1", textOverflow: "ellipsis" }}>{name}</Typography>
            <Typography variant="h6" sx={{ fontWeight: 400, marginLeft: "2%" }}>{price + "€"}</Typography>
            <IconButton color="primary" data-testid="add-icon" aria-label="add-to-cart" sx={{ zIndex: "99999999", border: "1px solid", marginLeft: "2%", width: "3.5vh", height: "3.5vh" }} onClick={handleAddProduct}>
                <AddShoppingCartIcon />
            </IconButton>
        </Box>
    </>
    );
}

export default SearchHit;
