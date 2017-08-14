import QtQuick 2.0

BorderImage {
    id: laserFenceImage
    width: 1000
    height: 700
    border { left: 25; top: 25; right: 25; bottom: 25 }
    horizontalTileMode: BorderImage.Repeat
    verticalTileMode: BorderImage.Repeat
    source: "qrc:///Images/laserfence.png"
}
