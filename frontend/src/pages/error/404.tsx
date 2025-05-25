import {
  Grid, Typography, Button
} from '@arco-design/web-react';
import { IconArrowLeft } from '@arco-design/web-react/icon';
import { useNavigate } from 'react-router-dom';
import styles from './error.module.less';

const { Row } = Grid;
const { Col } = Grid;

export default function Error404() {
  const navigate = useNavigate();

  return (
    <div className={styles.error}>
      <div className={styles['error-wrap']}>
        <Row gutter={16}>
          <Col xs={24} sm={24} md={24} lg={24} xl={24} xxl={24}>
            <div className={styles['error-content']}>
              <Typography.Title type="primary">抱歉！</Typography.Title>
              <Typography.Title heading={5}>当前页面不存在...</Typography.Title>
              <Typography.Text>
                请检查您输入的网址是否正确，或点击下面的按钮返回首页
              </Typography.Text>
              <div className={styles['back-home']}>
                <Button
                  shape="round"
                  type="primary"
                  onClick={() => navigate('/')}
                  icon={<IconArrowLeft />}
                >
                  返回首页
                </Button>
              </div>
            </div>
          </Col>
        </Row>
      </div>
    </div>
  );
}