import React, { useState, useEffect } from 'react';
import { Container, Row, Col, ListGroup, Button, Form, Modal } from 'react-bootstrap';
import apiClient from '../services/api';

function Checkout() {
    const [addresses, setAddresses] = useState([]);
    const [selectedAddressId, setSelectedAddressId] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showPaymentModal, setShowPaymentModal] = useState(false);
    const [editMode, setEditMode] = useState(false);  // 是否为编辑模式
    const [newAddress, setNewAddress] = useState({
        id: null,
        addressLine1: '',
        addressLine2: '',
        city: '',
        state: '',
        zipcode: '',
        mobile: '',
        contact: '',
        isDefault: false,
        notes: ''
    });
    const [paymentInfo, setPaymentInfo] = useState({
        cardNumber: '',
        expiryDate: '',
        cvv: '',
        cardHolderName: ''
    });

    useEffect(() => {
        // 获取当前用户的所有地址
        apiClient.get('/accounts/address')
            .then(response => {
                const addresses = response.data.content;
                setAddresses(addresses);

                // 设置默认地址为选中的地址
                const defaultAddress = addresses.find(address => address.isDefault);
                if (defaultAddress) {
                    setSelectedAddressId(defaultAddress.id);
                }
            })
            .catch(error => console.error('Error fetching addresses:', error));
    }, []);

    const handleAddressSelect = (addressId) => {
        setSelectedAddressId(addressId);
    };

    const handleAddAddress = () => {
        setEditMode(false);
        setNewAddress({
            id: null,
            addressLine1: '',
            addressLine2: '',
            city: '',
            state: '',
            zipcode: '',
            mobile: '',
            contact: '',
            isDefault: false,
            notes: ''
        });
        setShowModal(true);
    };

    const handleEditAddress = (address) => {
        setEditMode(true);
        setNewAddress(address);
        setShowModal(true);
    };

    const handleSaveAddress = () => {
        if (editMode) {
            // 编辑地址
            apiClient.put(`/accounts/address/${newAddress.id}`, newAddress)
                .then(response => {
                    setAddresses(addresses.map(addr => addr.id === newAddress.id ? response.data : addr));
                    setShowModal(false);
                })
                .catch(error => console.error('Error updating address:', error));
        } else {
            // 新增地址
            apiClient.post('/accounts/address', newAddress)
                .then(response => {
                    setAddresses([...addresses, response.data]);
                    setShowModal(false);
                })
                .catch(error => console.error('Error adding address:', error));
        }
    };

    const handleDeleteAddress = (addressId) => {
        apiClient.delete(`/accounts/address/${addressId}`)
            .then(() => {
                setAddresses(addresses.filter(address => address.id !== addressId));
            })
            .catch(error => console.error('Error deleting address:', error));
    };

    const handlePlaceOrder = () => {
        // 打开支付信息模态框
        setShowPaymentModal(true);
    };

    const handleConfirmOrder = () => {
        const orderData = {
            addressId: selectedAddressId,
            paymentInfo: paymentInfo
        };

        apiClient.post('/orders', orderData)
            .then(() => {
                // 下单成功，清空购物车
                apiClient.delete('/carts/clear')
                    .then(() => {
                        alert('Order placed successfully and cart cleared!');
                        setShowPaymentModal(false);
                        // 可能重定向到订单确认页面
                    })
                    .catch(error => console.error('Error clearing cart:', error));
            })
            .catch(error => console.error('Error placing order:', error));
    };

    return (
        <Container>
            <h2>Checkout</h2>
            <h4>Select Address</h4>
            <ListGroup>
                {addresses.map(address => (
                    <ListGroup.Item key={address.id}>
                        <Form.Check 
                            type="radio"
                            name="selectedAddress"
                            label={`${address.contact}: ${address.addressLine1}, ${address.city}, ${address.state}, ${address.zipcode}`}
                            checked={selectedAddressId === address.id}
                            onChange={() => handleAddressSelect(address.id)}
                        />
                        <Button 
                            variant="warning" 
                            className="ml-3"
                            onClick={() => handleEditAddress(address)}
                        >
                            Edit
                        </Button>
                        <Button 
                            variant="danger" 
                            className="ml-2"
                            onClick={() => handleDeleteAddress(address.id)}
                        >
                            Delete
                        </Button>
                    </ListGroup.Item>
                ))}
            </ListGroup>

            <Button variant="primary" className="mr-2" onClick={handleAddAddress}>
                Add New Address
            </Button>
            
            <Button variant="success" onClick={handlePlaceOrder}>
                    Place Order
            </Button>
            

            {/* 地址编辑/添加的模态框 */}
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{editMode ? 'Edit Address' : 'Add New Address'}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="contact">
                            <Form.Label>Contact</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.contact} 
                                onChange={(e) => setNewAddress({...newAddress, contact: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="addressLine1">
                            <Form.Label>Address Line 1</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.addressLine1} 
                                onChange={(e) => setNewAddress({...newAddress, addressLine1: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="addressLine2">
                            <Form.Label>Address Line 2</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.addressLine2} 
                                onChange={(e) => setNewAddress({...newAddress, addressLine2: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="city">
                            <Form.Label>City</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.city} 
                                onChange={(e) => setNewAddress({...newAddress, city: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="state">
                            <Form.Label>State</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.state} 
                                onChange={(e) => setNewAddress({...newAddress, state: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="zipcode">
                            <Form.Label>Zipcode</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.zipcode} 
                                onChange={(e) => setNewAddress({...newAddress, zipcode: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="mobile">
                            <Form.Label>Mobile</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.mobile} 
                                onChange={(e) => setNewAddress({...newAddress, mobile: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="notes">
                            <Form.Label>Notes</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={newAddress.notes} 
                                onChange={(e) => setNewAddress({...newAddress, notes: e.target.value})}
                            />
                        </Form.Group>

                        <Form.Group controlId="isDefault">
                            <Form.Check 
                                type="checkbox" 
                                label="Set as default address" 
                                checked={newAddress.isDefault}
                                onChange={(e) => setNewAddress({...newAddress, isDefault: e.target.checked})}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleSaveAddress}>
                        Save Address
                    </Button>
                </Modal.Footer>
            </Modal>

            {/* 支付信息的模态框 */}
            <Modal show={showPaymentModal} onHide={() => setShowPaymentModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Payment Information</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="cardHolderName">
                            <Form.Label>Card Holder Name</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={paymentInfo.cardHolderName} 
                                onChange={(e) => setPaymentInfo({...paymentInfo, cardHolderName: e.target.value})}
                            />
                        </Form.Group>
                        <Form.Group controlId="cardNumber">
                            <Form.Label>Card Number</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={paymentInfo.cardNumber} 
                                onChange={(e) => setPaymentInfo({...paymentInfo, cardNumber: e.target.value})}
                            />
                        </Form.Group>
                        <Form.Group controlId="expiryDate">
                            <Form.Label>Expiry Date</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={paymentInfo.expiryDate} 
                                onChange={(e) => setPaymentInfo({...paymentInfo, expiryDate: e.target.value})}
                            />
                        </Form.Group>
                        <Form.Group controlId="cvv">
                            <Form.Label>CVV</Form.Label>
                            <Form.Control 
                                type="text" 
                                value={paymentInfo.cvv} 
                                onChange={(e) => setPaymentInfo({...paymentInfo, cvv: e.target.value})}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowPaymentModal(false)}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={handleConfirmOrder}>
                        Confirm Order
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
}

export default Checkout;
