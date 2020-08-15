/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int numSegments;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (isNullPoints(points))
            throw new IllegalArgumentException("points cannot be null");

        Point[] copyPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyPoints);
        if (isDuplicatePoints(copyPoints))
            throw new IllegalArgumentException("points cannot be duplicated");

        numSegments = 0;
        lineSegments = new LineSegment[points.length];

        for (Point startingPoint : points) {
            Arrays.sort(copyPoints, startingPoint.slopeOrder());

            double slope = copyPoints[0].slopeTo(startingPoint);
            double currentSlope;
            int count = 0;
            int length = copyPoints.length;
            Point[] pointList = new Point[length];

            for (int i = 0; i < length; i++) {
                if (startingPoint.compareTo(copyPoints[i]) == 0)
                    continue;

                Point currentPoint = copyPoints[i];
                currentSlope = currentPoint.slopeTo(startingPoint);

                if (currentSlope == slope)
                    pointList[count++] = currentPoint;

                if (currentSlope != slope || i == length - 1) {
                    if (count >= 3)
                        addSegment(pointList, startingPoint, count);
                    count = 0;
                    pointList = new Point[length];
                    pointList[count++] = currentPoint;
                    slope = currentPoint.slopeTo(startingPoint);
                }
            }
        }
    }


    private void addSegment(Point[] pointList, Point startingPoint, int count) {
        // add startingPoint to pointList as it also needs to be a part of the sort
        pointList[count] = startingPoint;

        // transfer pointList elements to new array with adjusted size
        Point[] adjPointList = Arrays.copyOf(pointList, count + 1);

        // sort this array to get minPoint and maxPoint
        Arrays.sort(adjPointList);
        Point minPoint = adjPointList[0];
        Point maxPoint = adjPointList[count];

        // to avoid duplication
        if (minPoint.compareTo(startingPoint) == 0)
            lineSegments[numSegments++] = new LineSegment(startingPoint, maxPoint);
    }


    public int numberOfSegments() {
        return numSegments;
    }


    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, numSegments);
    }


    private boolean isNullPoints(Point[] points) {
        if (points == null)
            return true;
        for (Point point : points)
            if (point == null)
                return true;
        return false;
    }


    private boolean isDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0)
                return true;
        return false;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
