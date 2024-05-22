
import React from 'react';
import Typography from '@mui/material/Typography';
import Product from './Product';
import { useAppSelector } from '../../../hooks';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import { Box } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import { deleteProduct, getProductPhoto } from '../../../api/ProductRequest';
import { getTranslation } from '../../../../i18n/i18n';
import NoPhoto from "../../../../img/no_photo.jpg";
import { useNavigate } from 'react-router-dom';

const MyProductCard = ({ objectID, name, price, categories, description }: Product) => {
    const [isDeleted, setIsDeleted] = React.useState<boolean>(false);
    const [photo, setPhoto] = React.useState(NoPhoto);
    const navigate = useNavigate();
    const lang = useAppSelector(state => state.lang.lang);

    const jwt = useAppSelector(state => state.jwt);

    const handleAlertClick = () => {
        setIsDeleted(false);
    };

    const handleDeleteProduct = async (event: any) => {
        event.stopPropagation();
        setIsDeleted(false);

        const response = await deleteProduct(jwt.token, objectID);

        if (response.status) {
            if (response.status == 401) {
                navigate("/login");
            }
        }

        if (response.status == 200) {
            setIsDeleted(true);
        }

        location.reload();
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

    const handleEditProduct = () => {
        navigate("/product/edit", {
            state: {
                id: objectID,
                title: name,
                categories: categories,
                price: price,
                description: description
            }
        })
    }

    return (<>
        {isDeleted &&
            <Snackbar open={isDeleted} autoHideDuration={2000} onClose={handleAlertClick}>
                <Alert onClose={handleAlertClick} id="alert-success" severity="success" sx={{ width: '100%' }}>
                    {getTranslation(lang, "cartitem_deleted_successfully")}
                </Alert>
            </Snackbar>}
        <div onClick={handleEditProduct} className="my-product-card">
            <Box sx={{ display: "flex", flexDirection: "row", justifyContent: "space-between", alignItems: "center", width: "100%" }}>
                <Box height={"5vh"} sx={{ aspectRatio: "1/1" }}>
                    <img width={"100%"} height={"100%"} className="card-image" src={photo} alt="product-image" data-testid={"card-image-" + objectID} />
                </Box>
                <Typography variant="h6" sx={{ fontWeight: 200, width: "35%", ml: "1.5%", overflow: "hidden", lineHeight: "1", textOverflow: "ellipsis" }}>{name}</Typography>
                <Typography variant="h6" sx={{ fontWeight: 400, marginLeft: "2%" }}>{price + "€"}</Typography>
                <IconButton color="error" data-testid="delete-icon" aria-label="delete" sx={{ zIndex: "99999999", border: "1px solid", marginLeft: "2%", width: "3.5vh", height: "3.5vh" }} onClick={(e) => handleDeleteProduct(e)}>
                    <DeleteIcon />
                </IconButton>
            </Box>
        </div>
    </>
    );
}

export default MyProductCard;
