package com.isotop.storage.service.directory

import com.isotop.storage.config.exceptionHandlers.exception.ValidationException
import com.isotop.storage.dto.request.UpdateMotionRequest
import com.isotop.storage.dto.response.ListMoutionDataResponse
import com.isotop.storage.dto.response.ListMoutionTypeDataResponse
import com.isotop.storage.dto.response.MoutionResponse
import com.isotop.storage.repository.MoutionRepository
import com.isotop.storage.repository.StorageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class MoutionService(
    private val moutionRepository: MoutionRepository,
    private val storageRepository: StorageRepository
) {
    open fun getMoutionTypes(): ListMoutionTypeDataResponse {
        return ListMoutionTypeDataResponse(moutionRepository.getMoutionTypes())
    }

    open fun getAllMotions(): ListMoutionDataResponse {
        return ListMoutionDataResponse(moutionRepository.getMoutions())
    }

    open fun updateMotionByCode(payload: UpdateMotionRequest): MoutionResponse {
        if (!moutionRepository.isExistMotionByMotionCode(payload.moutionCode)) {
            throw ValidationException(34)
        }
        return moutionRepository.updateMoutionByMotionCode(payload)
    }

    @Transactional
    open fun deleteMotionByMotionCode(motionCode: Int) {
        if (!moutionRepository.isExistMotionByMotionCode(motionCode)) {
            throw ValidationException(34)
        }
        val motionTypeAndStorageCode =
            moutionRepository.getMoutionTypeAndStorageCodeByMotionCodeId(motionCode)
        when (motionTypeAndStorageCode.moutionType) {
            1 -> {
                moutionRepository.removeMoutionsByMoutionCode(motionCode)
                storageRepository.removeStorageNote(motionTypeAndStorageCode.storageCode)
            }
            2 -> {
                storageRepository.updateStorageComeDateToNull(motionTypeAndStorageCode.storageCode)
                moutionRepository.removeMoutionsByMoutionCode(motionCode)
            }
            3, 4, 5 -> {
                storageRepository.updateStorageLeaveDateToNull(motionTypeAndStorageCode.storageCode)
                moutionRepository.removeMoutionsByMoutionCode(motionCode)
            }
        }
    }
}
