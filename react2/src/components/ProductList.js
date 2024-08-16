import React, { useEffect, useState } from 'react';
import { Row, Col, Pagination, Alert } from 'react-bootstrap';
import apiClient from '../services/api'; // 引入你创建的 apiClient 实例
import ProductCard from './ProductCard';

function ProductList() {
    const [products, setProducts] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [pageSize] = useState(3);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchProducts(currentPage);
    }, [currentPage]);

    const fetchProducts = (page) => {
        apiClient.get(`/items?page=${page}&size=${pageSize}`)
            .then(response => {
                setProducts(response.data.content);
                setTotalPages(response.data.totalPages);
                setError(null); // 清除之前的错误
            })
            .catch(error => {
                setError('Error fetching products, please try again later.');
                console.error('Error fetching products:', error);
            });
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <div>
            {error && <Alert variant="danger">{error}</Alert>}
            <Row>
                {products.map(product => (
                    <Col key={product.id} sm={12} md={6} lg={4}>
                        <ProductCard product={product} />
                    </Col>
                ))}
            </Row>

            <Pagination className="mt-4">
                <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
                <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />
                {[...Array(totalPages)].map((_, index) => (
                    <Pagination.Item key={index} active={index === currentPage} onClick={() => handlePageChange(index)}>
                        {index + 1}
                    </Pagination.Item>
                ))}
                <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages - 1} />
                <Pagination.Last onClick={() => handlePageChange(totalPages - 1)} disabled={currentPage === totalPages - 1} />
            </Pagination>
        </div>
    );
}

export default ProductList;
