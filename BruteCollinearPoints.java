/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {
    private int numSegments;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (isNullPoint(points))
            throw new IllegalArgumentException("point cannot be null");

        Point[] copyPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyPoints);
        if (isDuplicatePoint(copyPoints))
            throw new IllegalArgumentException("points cannot be duplicated");

        numSegments = 0;
        lineSegments = new LineSegment[copyPoints.length];

        for (int p = 0; p < copyPoints.length - 3; p++) {
            for (int q = p + 1; q < copyPoints.length - 2; q++) {
                for (int r = q + 1; r < copyPoints.length - 1; r++) {
                    for (int s = r + 1; s < copyPoints.length; s++) {
                        Point p1 = copyPoints[p];
                        Point q1 = copyPoints[q];
                        Point r1 = copyPoints[r];
                        Point s1 = copyPoints[s];

                        if (areCollinear(p1, q1, r1, s1))
                            addSegment(p1, q1, r1, s1);
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, numSegments);
    }

    private boolean areCollinear(Point p, Point q, Point r, Point s) {
        if (Double.compare(p.slopeTo(q), q.slopeTo(r)) == 0 &&
                Double.compare(q.slopeTo(r), r.slopeTo(s)) == 0)
            return true;
        return false;
    }

    private void addSegment(Point p, Point q, Point r, Point s) {
        Point[] collinearPoints = { p, q, r, s };
        Arrays.sort(collinearPoints);
        Point minPoint = collinearPoints[0];
        Point maxPoint = collinearPoints[3];

        lineSegments[numSegments++] = new LineSegment(minPoint, maxPoint);
    }

    private boolean isNullPoint(Point[] points) {
        if (points == null)
            return true;

        for (Point point : points) {
            if (point == null)
                return true;
        }
        return false;
    }

    private boolean isDuplicatePoint(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                return true;
        }
        return false;
    }
}
