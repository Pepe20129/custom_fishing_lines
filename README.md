A mod for data driven color patterns on fishing rod lines.

The format is as follows (note that the actual format is json and not json5 so comments are not allowed):
```json5
{
    "segments": [
        {
            //the color of the segment in "rrggbb" format
            "color": "",
            /*
             * the percentage of the fishing rod line
             * 
             * 1 is the part of the fishing rod line
             * attached to the fishing rod and 0 is
             * the part of the fishing rod attached to
             * the bobber
             * 
             * segments have to be ordered from higher
             * to lower percentage
             */
            "percentage": 0
        },
        ...
    ]
}
```