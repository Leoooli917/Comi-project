import * as React from 'react';
import './SingleColumnImageView.less'

const SingleColumnImageView = ({ images = [], width = 90, height = 90, showLabel = false, onImageClick }) => {
      // 检查是否传递了必要的属性  
      if (!Array.isArray(images) || images.length === 0 || !width || !height) {
            return <div>No images or dimensions to display.</div>;
      }

      return (
            <div className='single-column-layout'>
                  {images.map((image, index) => (
                        <div className='single-column-card-container' key={`comi`+index}>
                              {<div className='single-column-card-label'>{((index < 9) ? '0' + (index + 1) : (index + 1))}</div>}
                              {image.isSelected && (<img className='selceted-img-bg' src='https://p5.ssl.qhimg.com/t01bd6ebe4ea0e1f479.png'></img>)}
                              <div className='single-column-card' key={index}>
                              
                                    {image.imgUrl && <div className={image.isSelected ? 'single-column-card-img-bg' : 'normal-page '} style={{ width: `${width}px`, height: `${height}px` }}
                                    >
                                          <img src={image.imgUrl}
                                                alt={`Image ${index + 1}`}
                                                style={{ width: `100%`, height: `100%`, cursor: 'pointer', objectFit: 'contain' }}
                                                onClick={() => onImageClick(image, index)} // 调用传递的回调函数，并传递当前图片和索引  
                                          />
                                          {image.isPoster === 1 && <img className='poster-tag' src='https://p2.ssl.qhimg.com/t0125ed63c507b78c36.png'></img>}
                                    </div>}
                                    {!image.imgUrl && <div className='loading' style={{ width: `${width}px`, height: `${height}px` }}>
                                          <img src="https://p0.ssl.qhimg.com/t0193e7a01b10889674.png" alt="" />
                                    </div>}
                              </div>
                        </div>
                  ))}
            </div>
      );
};

export default SingleColumnImageView;