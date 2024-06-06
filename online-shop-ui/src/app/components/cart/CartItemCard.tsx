import React from 'react';
import Typography from '@mui/material/Typography';
import { useAppSelector } from '../../hooks';
import { deleteCartItem, changeQuantity } from '../../api/CartItemsRequest';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import QuantityInput from '../QuantityInput';
import { getProductPhoto } from '../../api/ProductRequest';
import { Box } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import { getTranslation } from '../../../i18n/i18n';
import NoPhoto from "../../../img/no_photo.jpg";
import { useNavigate } from 'react-router';

interface CartItemProps {
  id: number,
  productId: number,
  title: string,
  price: string,
  quantity: number
}

const CartItemCard = ({ id, productId, title, price, quantity }: CartItemProps) => {
  const [isDeleted, setIsDeleted] = React.useState<boolean>(false);
  const [photo, setPhoto] = React.useState(NoPhoto);
  const navigate = useNavigate();
  const lang = useAppSelector(state => state.lang.lang);

  const jwt = useAppSelector(state => state.jwt);

  const handleAlertClick = () => {
    setIsDeleted(false);
  };

  const handleDeleteProduct = async () => {
    setIsDeleted(false);

    const response = await deleteCartItem(jwt.token, id);

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

  const handleSaveProduct = async () => {
    const updatedQuantity = (document.getElementById(`quantity-input-${id}`) as HTMLInputElement).value;
    const response = await changeQuantity(jwt.token, id, parseInt(updatedQuantity));

    if (response.status) {
      if (response.status == 401) {
        navigate("/login");
      }
    }
  }

  React.useEffect(() => {
    const getProductPhotoRequest = async () => {
      const photoBlob = await getProductPhoto(productId);

      if ((photoBlob as unknown as Response).status) {
        if ((photoBlob as unknown as Response).status == 401) {
          navigate("/login");
        }
      }

      if (photoBlob.size > 0) {
        setPhoto(URL.createObjectURL(photoBlob));
      }
    }

    getProductPhotoRequest();
  }, []);

  return (<>
    {isDeleted &&
      <Snackbar open={isDeleted} autoHideDuration={2000} onClose={handleAlertClick}>
        <Alert onClose={handleAlertClick} severity="success" sx={{ width: '100%' }} id="alert-success">
          {getTranslation(lang, "cartitem_deleted_successfully")}
        </Alert>
      </Snackbar>}
    <Box sx={{ display: "flex", flexDirection: "row", justifyContent: "space-between", alignItems: "center", width: "100%" }}>
      <Box height={"5vh"} sx={{ aspectRatio: "1/1" }}>
        <img width={"100%"} height={"100%"} className="card-image" src={photo} alt="cart-item-image" data-testid={"card-image-" + id} />
      </Box>
      <Typography variant="h6" sx={{ fontWeight: 200, width: "35%", ml: "1.5%", overflow: "hidden", lineHeight: "1", textOverflow: "ellipsis" }}>{title}</Typography>
      <QuantityInput id={id} defaultValue={quantity} handleSaveProduct={handleSaveProduct} />
      <Typography variant="h6" sx={{ fontWeight: 400, marginLeft: "2%" }}>{price + "$"}</Typography>
      <IconButton color="error" aria-label="delete from shopping cart" data-testid="delete-icon" sx={{ border: "1px solid", marginLeft: "2%", width: "3.5vh", height: "3.5vh" }} onClick={handleDeleteProduct}>
        {/*<DeletebIcon />*/}
      </IconButton>
    </Box>
  </>
  );
}

export default CartItemCard;
