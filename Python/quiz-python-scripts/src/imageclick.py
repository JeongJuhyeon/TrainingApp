import cv2 as cv

def imageClickRegions(image_name, image_wh_tuple):
    img = cv.imread("image_name")
    img = cv.resize(img, dsize=image_wh_tuple, interpolation=cv.INTER_AREA)
    button_areas = cv.selectROIs("Enter to confirm", img, True, False)
    print('button_areas =', button_areas)

    for r in button_areas:
        cv.rectangle(img, (r[0], r[1]), (r[0] + r[2], r[1] + r[3]), 255)
        ##    img = img[r[1]:r[1]+r[3], r[0]:r[0]+r[2]]
    ##    cv.imshow('Img', img)
    ##    cv.waitKey()

    cv.imshow('img', img)
    cv.waitKey()
    cv.destroyAllWindows()

    return button_areas

if __name__ == '__main__':
    imageClickRegions()