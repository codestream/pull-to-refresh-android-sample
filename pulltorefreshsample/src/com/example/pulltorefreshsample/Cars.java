package com.example.pulltorefreshsample;

import android.graphics.Bitmap;

public class Cars {
	
	private String mCarId;
	private String mCarName;
	private String mCarUrl;
	private Bitmap mCarImage;
	
	public Cars(String mCarId, String mCarName, String mCarUrl, Bitmap mCarImage) {
		this.mCarId = mCarId;
		this.mCarName = mCarName;
		this.mCarUrl = mCarUrl;
		this.mCarImage = mCarImage;
	}

	public String getCarId() {
		return mCarId;
	}

	public void setCarId(String mCarId) {
		this.mCarId = mCarId;
	}

	public String getCarName() {
		return mCarName;
	}

	public void setCarName(String mCarName) {
		this.mCarName = mCarName;
	}

	public String getCarUrl() {
		return mCarUrl;
	}

	public void setCarUrl(String mCarUrl) {
		this.mCarUrl = mCarUrl;
	}

	public Bitmap getCarImage() {
		return mCarImage;
	}

	public void setCarImage(Bitmap mCarImage) {
		this.mCarImage = mCarImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mCarId == null) ? 0 : mCarId.hashCode());
		result = prime * result
				+ ((mCarName == null) ? 0 : mCarName.hashCode());
		result = prime * result + ((mCarUrl == null) ? 0 : mCarUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cars other = (Cars) obj;
		if (mCarId == null) {
			if (other.mCarId != null) {
				return false;
			}
		} else if (!mCarId.equals(other.mCarId)) {
			return false;
		}
		if (mCarName == null) {
			if (other.mCarName != null) {
				return false;
			}
		} else if (!mCarName.equals(other.mCarName)) {
			return false;
		}
		if (mCarUrl == null) {
			if (other.mCarUrl != null) {
				return false;
			}
		} else if (!mCarUrl.equals(other.mCarUrl)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cars [mCarId=" + mCarId + ", mCarName=" + mCarName
				+ ", mCarUrl=" + mCarUrl + ", mCarImage=" + mCarImage + "]";
	}
}
